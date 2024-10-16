package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.service.RaiderIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaiderIoRestController {

    @Autowired
    private RaiderIoService raiderIoService;


    @GetMapping("/profileRaider")
    public ResponseEntity<String> getProfilePlayerFromRaider(String region, String realm, String name){
        return ResponseEntity.ok(raiderIoService.getPlayerProfile(region, realm, name).getBody());
    }

    @GetMapping("/membersRaider")
    public ResponseEntity<String> getMembers(){
        return ResponseEntity.ok(raiderIoService.getMembersOfGuildFromRealmInRegion("eu", "Cho'gall", "devienne fou").getBody());
    }

}
