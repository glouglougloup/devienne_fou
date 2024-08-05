package com.deviennefou.devienne_fou_weekly_check.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RaiderIOService {

    @Autowired
    private RestClient restClient;

    public ResponseEntity<String> getMembersFromGuildOfRealm(String region, String realm, String guild) {

        return restClient.get()
                //extra field [raid_progression/raid_encounters/raid_rankings/members] hardcoded
                .uri("https://raider.io/api/v1/characters/profile?region={region}&realm={realm}&guild={guild}&fields=members", region, realm, guild)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
    }
}
