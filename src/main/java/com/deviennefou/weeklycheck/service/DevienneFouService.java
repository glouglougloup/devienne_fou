package com.deviennefou.weeklycheck.service;

import com.deviennefou.weeklycheck.dto.*;
import com.deviennefou.weeklycheck.mapper.DevienneFouCharacterMapper;
import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import com.deviennefou.weeklycheck.model.MythicPlusRunHistory;
import com.deviennefou.weeklycheck.model.WeekStatus;
import com.deviennefou.weeklycheck.repository.DevienneFouRepository;
import com.deviennefou.weeklycheck.repository.MythicPlusRunHistoryRepository;
import com.deviennefou.weeklycheck.utils.DevienneFouDateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class DevienneFouService {

    private static final Logger log = LoggerFactory.getLogger(DevienneFouService.class);

    @Autowired
    RaiderIoService raiderIOService;

    @Autowired
    DevienneFouRepository devienneFouRepository;

    @Autowired
    MythicPlusRunHistoryRepository mythicPlusRunHistoryRepository;

    @Autowired
    DevienneFouCharacterMapper devienneFouCharacterMapper;

    public List<MemberDTO> getMembers() {
        List<DevienneFouCharacter> members = devienneFouRepository.findAll();
        return members.stream().map(
                character -> new MemberDTO(character.getName(),
                        character.getRegion(),
                        character.getRealm(),
                        getCurrentWeekStatus(character, LocalDate.now()))
        ).toList();
    }

    public List<DevienneFouCharacter> getMembers(String filter){
        if(filter != null && !filter.isEmpty()){
            return devienneFouRepository.findByName(filter).stream().filter(Objects::nonNull).toList();
        }else{
            return devienneFouRepository.findAll();
        }
    }

    public List<MythicPlusRunHistoryDTO> getHistory(String playerName,LocalDate localDate){
        List<MythicPlusRunHistory> historyByPlayerName;
        if(localDate == null){
            historyByPlayerName = mythicPlusRunHistoryRepository.findByPlayerName(playerName);
        }else{
            historyByPlayerName = mythicPlusRunHistoryRepository.findByPlayerNameAndWeekStart(
                    playerName,
                    DevienneFouDateUtils.getWeekStart(localDate)
            );
        }
        return historyByPlayerName.stream()
                .map(run -> new MythicPlusRunHistoryDTO(
                        run.getWeekStart(),
                        run.getWeekStatus().toString(),
                        run.getRecordedAt()))
                .toList();
    }

    public WeekStatus getCurrentWeekStatus(DevienneFouCharacter character, LocalDate localDate){
        Optional<MythicPlusRunHistory> byDevienneFouCharacterAndWeekStart = mythicPlusRunHistoryRepository.findByDevienneFouCharacterAndWeekStart(character, DevienneFouDateUtils.getWeekStart(localDate));
        if(byDevienneFouCharacterAndWeekStart.isPresent()){
            return byDevienneFouCharacterAndWeekStart.get().getWeekStatus();
        }else{
            throw new IllegalArgumentException("There's not record at " + localDate + " for player " + character.getName());
        }
    }

    public String deletePlayerById(Long id) {
        devienneFouRepository.deleteById(id);
        return "Player with id " + id + " has been remove.";
    }

    public String synchronizeDatabaseWithRaiderIoApi(){
        List<DevienneFouCharacter> rosterList = devienneFouRepository.findAll();

        rosterList
                .forEach(character -> {
                    Optional<ProfileCharacterRaiderIo> profile = getProfile(
                            character.getRegion(),
                            character.getRealm(),
                            character.getName()
                    );

                    profile.map(profileCharacterRaiderIo -> {
                        Optional<DevienneFouCharacter> existingPlayerOptional = devienneFouRepository.findByRegionAndRealmAndName(
                                profileCharacterRaiderIo.region(),
                                profileCharacterRaiderIo.realm(),
                                profileCharacterRaiderIo.name()
                        );

                        if (existingPlayerOptional.isPresent()) {
                            DevienneFouCharacter existingPlayer = existingPlayerOptional.get();
                            calculateRunHistory(existingPlayer,profileCharacterRaiderIo,mythicPlusRunHistoryRepository);
                            return existingPlayer;
                        } else {
                            return devienneFouCharacterMapper.toDevienneFouCharacterEntity(profileCharacterRaiderIo);
                        }
                    });
                });

        return "Synchronized " + rosterList.size() + " players in the DB";
    }

    public String fetchAndSynchronizeDatabaseWithRaiderIoApi(ResponseEntity<String> membersOfGuildFromRealmInRegion) {
        if(membersOfGuildFromRealmInRegion.getStatusCode().is4xxClientError()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"response from RaiderIO API");
        }

        String body = membersOfGuildFromRealmInRegion.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        GuildResponseRaiderIo guildResponseRaiderIo;
        try {
            guildResponseRaiderIo = objectMapper.readValue(body, GuildResponseRaiderIo.class);
        } catch (IOException e) {
            log.error("Could not map the responseEntity from raiderIO api to our model GuildResponseRaiderIo ", e);
            return "Could not map the responseEntity from raiderIO api to our model GuildResponseRaiderIo ";
        }
        List<MemberRaiderIo> memberRaiderIoList = guildResponseRaiderIo.members().stream().toList();
        Set<Integer> validRanks = Set.of(1,3,4,5);
        List<DevienneFouCharacter> rosterList = memberRaiderIoList.stream()
                .filter(memberRaiderIo -> validRanks.contains(memberRaiderIo.rank()))
                .map(memberRaiderIo -> {
                    Optional<ProfileCharacterRaiderIo> profile = getProfile(
                            memberRaiderIo.character().region(),
                            memberRaiderIo.character().realm(),
                            memberRaiderIo.character().name()
                    );

                    return profile.map(profileCharacterRaiderIo -> {
                        Optional<DevienneFouCharacter> existingPlayerOptional = devienneFouRepository.findByRegionAndRealmAndName(
                                profileCharacterRaiderIo.region(),
                                profileCharacterRaiderIo.realm(),
                                profileCharacterRaiderIo.name()
                        );

                        if (existingPlayerOptional.isPresent()) {
                            DevienneFouCharacter existingPlayer = existingPlayerOptional.get();
                            calculateRunHistory(existingPlayer,profileCharacterRaiderIo,mythicPlusRunHistoryRepository);
                            return existingPlayer;
                        } else {
                            return devienneFouCharacterMapper.toDevienneFouCharacterEntity(profileCharacterRaiderIo);
                        }
                    }).orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();

        try {
            devienneFouRepository.saveAll(rosterList);
        }catch (Exception e){
            throw new RuntimeException("Could not save members in the DB : " + e.getMessage());
        }

        return "Fetch and saved " + rosterList.size() + " players in DB.";
    }

    public Optional<ProfileCharacterRaiderIo> getProfile(String region, String realm, String name) {

        ResponseEntity<String> responseEntityMembersRaiderIo;
        try {
            responseEntityMembersRaiderIo = raiderIOService.getPlayerProfile(region, realm, name);
        } catch (HttpClientErrorException e) {
            log.warn("RaiderIO API returned an error for player {}: {}", name, e.getStatusCode());
            return Optional.empty();
        }


        String body = responseEntityMembersRaiderIo.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        ProfileCharacterRaiderIo profileCharacterRaiderIo;
        try {
            profileCharacterRaiderIo = objectMapper.readValue(body, ProfileCharacterRaiderIo.class);
        } catch (IOException e) {
            log.error("Could not map the responseEntity from raiderIO api to our model ProfileCharacterRaiderIo ", e);
            return Optional.empty();
        }

        return Optional.of(profileCharacterRaiderIo);
    }

    static void calculateRunHistory(DevienneFouCharacter entity, ProfileCharacterRaiderIo dto, MythicPlusRunHistoryRepository mythicPlusRunHistoryRepository){
        int totalRuns = dto.mythicWeeklyHighestLevelRuns() != null ? dto.mythicWeeklyHighestLevelRuns().length : 0;
        int level10OrAboveRuns = 0;

        if (dto.mythicWeeklyHighestLevelRuns() != null) {
            level10OrAboveRuns = Math.toIntExact(
                    Arrays.stream(dto.mythicWeeklyHighestLevelRuns())
                            .filter(run -> run.mythic_level() >= 10)
                            .count()
            );
        }

        WeekStatus weekStatus;
        if (totalRuns < 4) {
            weekStatus = WeekStatus.BAD;
        } else if (level10OrAboveRuns >= 4) {
            weekStatus = WeekStatus.VALIDATED;
        } else {
            weekStatus = WeekStatus.WARNING;
        }

        Date weekStart = DevienneFouDateUtils.getWeekStart();

        Optional<MythicPlusRunHistory> existingRunHistory = mythicPlusRunHistoryRepository.findByDevienneFouCharacterAndWeekStart(entity, weekStart);

        if (existingRunHistory.isPresent()){
            MythicPlusRunHistory history = existingRunHistory.get();
            history.setWeekStatus(weekStatus);
            history.setRecordedAt(new Date());
            mythicPlusRunHistoryRepository.save(history);
        }else {
            MythicPlusRunHistory newHistory = MythicPlusRunHistory.builder()
                    .devienneFouCharacter(entity)
                    .weekStatus(weekStatus)
                    .recordedAt(new Date())
                    .weekStart(weekStart)
                    .build();

            mythicPlusRunHistoryRepository.save(newHistory);
        }
    }


}
