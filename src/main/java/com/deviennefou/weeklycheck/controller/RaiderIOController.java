package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.model.CharacterRaiderIo;
import com.deviennefou.weeklycheck.model.MemberRaiderIo;
import com.deviennefou.weeklycheck.service.DevienneFouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class RaiderIOController {
    @Autowired
    DevienneFouService devienneFouService;

    @GetMapping("/rank")
    public ResponseEntity<List<MemberRaiderIo>> getMembersByRank(@RequestParam int rank){
        Optional<List<MemberRaiderIo>> membersWithRanks = devienneFouService.getMembersWithRanks(rank);

        return membersWithRanks.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/name")
    public ResponseEntity<CharacterRaiderIo> getProfileByName(@RequestParam String name){
        Optional<CharacterRaiderIo> profile = devienneFouService.getProfile(name);

        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
