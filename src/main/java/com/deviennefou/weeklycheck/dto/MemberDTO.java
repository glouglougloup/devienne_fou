package com.deviennefou.weeklycheck.dto;

import com.deviennefou.weeklycheck.model.WeekStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberDTO(
        String name,
        String region,
        String realm,
        WeekStatus weekStatus
) {
}
