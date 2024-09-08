package com.deviennefou.weeklycheck.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileCharacterRaiderIo(
        String name,
        String race,
        @JsonProperty("class")
        String wowClass,
        @JsonProperty("profile_url")
        String profileUrl,
        String region,
        String realm,
        MythicWeeklyHighestLevelRun[] mythicWeeklyHighestLevelRuns,
        MythicPreviousWeeklyHighestLevelRun[] mythicPreviousWeeklyHighestLevelRuns){

}