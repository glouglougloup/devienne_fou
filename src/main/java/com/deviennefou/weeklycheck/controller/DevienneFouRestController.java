package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.dto.MemberCharacterRaiderIo;
import com.deviennefou.weeklycheck.mapper.DevienneFouCharacterMapper;
import com.deviennefou.weeklycheck.model.DevienneFouCharacter;
import com.deviennefou.weeklycheck.service.DevienneFouService;
import com.deviennefou.weeklycheck.service.RaiderIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DevienneFouRestController {

    @Autowired
    private DevienneFouService devienneFouService;

    @Autowired
    private RaiderIoService raiderIoService;

    @Autowired
    private DevienneFouCharacterMapper devienneFouCharacterMapper;

    @GetMapping("/members")
    public ResponseEntity<List<MemberCharacterRaiderIo>> getMembers() {
        List<DevienneFouCharacter> members = devienneFouService.getMembers();
        List<MemberCharacterRaiderIo> list = members.stream()
                .map(character -> devienneFouCharacterMapper.toDevienneFouCharacterDto(character))
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/synchronize")
    public ResponseEntity<String> synchronizeWithRaiderApi() {
        return ResponseEntity.ok(
                devienneFouService.synchronizeDatabaseWithRaiderIoApi(
                        raiderIoService.getMembersOfGuildFromRealmInRegion("eu", "Cho'gall", "devienne fou"))
        );
    }
}
