package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.service.DevienneFouService;
import com.deviennefou.weeklycheck.service.RaiderIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaiderIoRestController {
    @Autowired
    private DevienneFouService devienneFouService;

    @Autowired
    private RaiderIoService raiderIoService;

    @GetMapping("/profileDB")
    public ResponseEntity<String> getProfilePlayerFromDB(String region, String realm, String name){
        return ResponseEntity.ok(devienneFouService.getProfile(region,realm,name).toString());
    }

    @GetMapping("/profileRaider")
    public ResponseEntity<String> getProfilePlayerFromRaider(String region, String realm, String name){
        return ResponseEntity.ok(raiderIoService.getPlayerProfile(region, realm, name).getBody());
    }

    @GetMapping("/membersRaider")
    public ResponseEntity<String> getMembers(){
        return ResponseEntity.ok(raiderIoService.getMembersOfGuildFromRealmInRegion("eu", "Cho'gall", "devienne fou").getBody());
    }

}
