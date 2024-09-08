package com.deviennefou.weeklycheck.repository;

import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import com.deviennefou.weeklycheck.model.MythicPlusRunHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface MythicPlusRunHistoryRepository extends JpaRepository<MythicPlusRunHistory, Long> {

    Optional<MythicPlusRunHistory> findByDevienneFouCharacterAndWeekStart(DevienneFouCharacter devienneFouCharacter, Date weekStart);
}
