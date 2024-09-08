package com.deviennefou.weeklycheck.repository;

import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevienneFouRepository extends JpaRepository<DevienneFouCharacter, Long> {

    Optional<DevienneFouCharacter> findByRegionAndRealmAndName(String region, String realm, String name);
}
