package com.deviennefou.weeklycheck.mapper;

import com.deviennefou.weeklycheck.dto.MemberCharacterRaiderIo;
import com.deviennefou.weeklycheck.dto.ProfileCharacterRaiderIo;
import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import com.deviennefou.weeklycheck.model.MythicPlusRunHistory;
import com.deviennefou.weeklycheck.model.WeekStatus;
import com.deviennefou.weeklycheck.utils.DevienneFouDateUtils;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface DevienneFouCharacterMapper {

    @Mapping(target = "runHistoryList", ignore = true)
    DevienneFouCharacter toDevienneFouCharacterEntity(ProfileCharacterRaiderIo memberRaiderIo);

    MemberCharacterRaiderIo toDevienneFouCharacterDto(DevienneFouCharacter devienneFouCharacter);

    @AfterMapping
    default void calculateRunHistory(@MappingTarget DevienneFouCharacter entity, ProfileCharacterRaiderIo dto){
        List<MythicPlusRunHistory> runHistories = new ArrayList<>();
        int totalRuns = dto.mythicWeeklyHighestLevelRuns() != null ? dto.mythicPreviousWeeklyHighestLevelRuns().length : 0;
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

        MythicPlusRunHistory history = MythicPlusRunHistory.builder()
                .devienneFouCharacter(entity)
                .weekStatus(weekStatus)
                .recordedAt(new Date())
                .weekStart(DevienneFouDateUtils.getWeekStart())
                .build();

        runHistories.add(history);

        entity.setRunHistoryList(runHistories);
    }

}
