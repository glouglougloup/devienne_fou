package com.deviennefou.devienne_fou_weekly_check.service;

import com.deviennefou.devienne_fou_weekly_check.model.GuildResponse;
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
    RaiderIOService raiderIOService;


    public Optional<List<MemberRaiderIo>> getMembersWithRanks(int rank) {
        ResponseEntity<String> responseEntityMembersRaiderIo = raiderIOService.getMembersFromGuildOfRealm("eu", "Cho'gall", "devienne fou");

        if(responseEntityMembersRaiderIo.getStatusCode().is4xxClientError()){
            log.error("400 response from RaiderIO API");
            return Optional.empty();
        }

        String body = responseEntityMembersRaiderIo.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        GuildResponse guildResponse;
        try {
            guildResponse = objectMapper.readValue(body, GuildResponse.class);
        } catch (IOException e) {
            log.error("Could not map the responseEntity from raiderIO api", e);
            return Optional.empty();
        }
        List<MemberRaiderIo> filteredMembers = guildResponse.members().stream()
                .filter(member -> member.rank() == rank)
                .toList();

        return Optional.of(filteredMembers);
    }
}
