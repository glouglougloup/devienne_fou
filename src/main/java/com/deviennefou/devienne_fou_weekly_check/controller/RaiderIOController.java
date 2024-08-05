package com.deviennefou.devienne_fou_weekly_check.controller;

import com.deviennefou.devienne_fou_weekly_check.service.DevienneFouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaiderIOController {
    @Autowired
    DevienneFouService devienneFouService;

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@RequestParam int rank){
        devienneFouService.getMembersWithRanks(rank);

        //TODO replace String with model for front
        return ResponseEntity.ok("ok");
    }
}
