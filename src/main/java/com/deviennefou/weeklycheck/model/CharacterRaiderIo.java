package com.deviennefou.weeklycheck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CharacterRaiderIo(
        String name,
        String race,
        @JsonProperty("class")
        String wowClass,
        String profile_url,
        MythicWeeklyHighestLevelRun[] mythic_plus_weekly_highest_level_runs,
        MythicPreviousWeeklyHighestLevelRun[] mythic_plus_previous_weekly_highest_level_runs) {

}