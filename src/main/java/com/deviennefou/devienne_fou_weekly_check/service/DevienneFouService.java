package com.deviennefou.devienne_fou_weekly_check.service;

import com.deviennefou.devienne_fou_weekly_check.model.CharacterRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.model.GuildResponseRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.model.MemberRaiderIo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DevienneFouService {

    private static final Logger log = LoggerFactory.getLogger(DevienneFouService.class);
    @Autowired
    RaiderIoService raiderIOService;


    public Optional<List<MemberRaiderIo>> getMembersWithRanks(int rank) {
        ResponseEntity<String> responseEntityMembersRaiderIo = raiderIOService.getMembersOfGuildFromRealmInRegion("eu", "Cho'gall", "devienne fou");

        if(responseEntityMembersRaiderIo.getStatusCode().is4xxClientError()){
            log.error("400 response from RaiderIO API");
            return Optional.empty();
        }

        String body = responseEntityMembersRaiderIo.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        GuildResponseRaiderIo guildResponseRaiderIo;
        try {
            guildResponseRaiderIo = objectMapper.readValue(body, GuildResponseRaiderIo.class);
        } catch (IOException e) {
            log.error("Could not map the responseEntity from raiderIO api to our model GuildResponseRaiderIo ", e);
            return Optional.empty();
        }
        List<MemberRaiderIo> filteredMembers = guildResponseRaiderIo.members().stream()
                .filter(member -> member.rank() == rank)
                .toList();

        return Optional.of(filteredMembers);
    }

    public Optional<CharacterRaiderIo> getProfile(String name){
        ResponseEntity<String> responseEntityMembersRaiderIo = raiderIOService.getPlayerProfile("eu", "Cho'gall", name);

        if(responseEntityMembersRaiderIo.getStatusCode().is4xxClientError()){
            //TODO Implement ControllerAdvice for common error ?
            log.error("400 response from RaiderIO API");
            return Optional.empty();
        }

        String body = responseEntityMembersRaiderIo.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        CharacterRaiderIo characterRaiderIo;
        try {
            characterRaiderIo = objectMapper.readValue(body, CharacterRaiderIo.class);
        } catch (IOException e) {
            log.error("Could not map the responseEntity from raiderIO api to our model CharacterRaiderIo ", e);
            return Optional.empty();
        }

        return Optional.of(characterRaiderIo);
    }
}
