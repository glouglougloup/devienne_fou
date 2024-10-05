package com.deviennefou.weeklycheck.repository;

import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import com.deviennefou.weeklycheck.model.MythicPlusRunHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MythicPlusRunHistoryRepository extends JpaRepository<MythicPlusRunHistory, Long> {

    Optional<MythicPlusRunHistory> findByDevienneFouCharacterAndWeekStart(DevienneFouCharacter devienneFouCharacter, Date weekStart);

    @Query("SELECT h FROM MythicPlusRunHistory h JOIN h.devienneFouCharacter c WHERE c.name = :playerName ORDER BY h.id")
    List<MythicPlusRunHistory> findByPlayerName(@Param("playerName") String playerName);

    @Query("SELECT h FROM MythicPlusRunHistory h JOIN h.devienneFouCharacter c WHERE c.name = :playerName AND h.weekStart = :weekStart ORDER BY h.id")
    List<MythicPlusRunHistory> findByPlayerNameAndWeekStart(String playerName, Date weekStart);

}
