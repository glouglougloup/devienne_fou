package com.deviennefou.weeklycheck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RaiderIoService {

    @Autowired
    private RestClient restClient;

    public ResponseEntity<String> getMembersOfGuildFromRealmInRegion(String region, String realm, String guild) {

        return restClient.get()
                //extra field [raid_progression/raid_encounters/raid_rankings/members] hardcoded
                .uri("https://raider.io/api/v1/characters/profile?region={region}&realm={realm}&guild={guild}&fields=members", region, realm, guild)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<String> getPlayerProfile(String region, String realm, String name) {
        return restClient.get()
                //extra field [mythic_plus_weekly_highest_level_runs/mythic_plus_previous_weekly_highest_level_runs] hardcoded
                .uri("https://raider.io/api/v1/characters/profile?region={region}&realm={realm}&name={name}&fields=mythic_plus_weekly_highest_level_runs%2Cmythic_plus_previous_weekly_highest_level_runs",
                        region,realm,name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
    }
}
