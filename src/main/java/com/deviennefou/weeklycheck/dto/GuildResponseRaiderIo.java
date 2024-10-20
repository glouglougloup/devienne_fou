package com.deviennefou.weeklycheck.dto;

import java.util.List;

public record GuildResponseRaiderIo(
        String name,
        String faction,
        String region,
        String realm,
        String last_crawled_at,
        String profile_url,
        List<MemberRaiderIo> members
) {
}