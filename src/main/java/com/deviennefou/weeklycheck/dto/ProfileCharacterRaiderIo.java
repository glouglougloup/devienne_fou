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
        @JsonProperty("mythic_plus_weekly_highest_level_runs")
        MythicWeeklyHighestLevelRun[] mythicWeeklyHighestLevelRuns,
        @JsonProperty("mythic_plus_previous_weekly_highest_level_runs")
        MythicPreviousWeeklyHighestLevelRun[] mythicPreviousWeeklyHighestLevelRuns){

}