package com.deviennefou.devienne_fou_weekly_check.controller;

import com.deviennefou.devienne_fou_weekly_check.model.MemberRaiderIo;
import com.deviennefou.devienne_fou_weekly_check.service.DevienneFouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RaiderIOController {
    @Autowired
    DevienneFouService devienneFouService;

    @GetMapping("/profile")
    public ResponseEntity<List<MemberRaiderIo>> getProfile(@RequestParam int rank){
        Optional<List<MemberRaiderIo>> membersWithRanks = devienneFouService.getMembersWithRanks(rank);

        return membersWithRanks.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
