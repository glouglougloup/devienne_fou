package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.dto.MemberDTO;
import com.deviennefou.weeklycheck.dto.MythicPlusRunHistoryDTO;
import com.deviennefou.weeklycheck.mapper.DevienneFouCharacterMapper;
import com.deviennefou.weeklycheck.service.DevienneFouService;
import com.deviennefou.weeklycheck.service.RaiderIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<MemberDTO>> getMembers() {
        return ResponseEntity.ok(devienneFouService.getMembers());
    }

    @GetMapping("/history")
    public ResponseEntity<List<MythicPlusRunHistoryDTO>> getHistory(@RequestParam String playerName,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        List<MythicPlusRunHistoryDTO> history = devienneFouService.getHistory(playerName,date);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/synchronize")
    public ResponseEntity<String> synchronizeWithRaiderApi() {
        return ResponseEntity.ok(devienneFouService.synchronizeDatabaseWithRaiderIoApi());
    }

    @PostMapping("/fetchAndSynchronize")
    public ResponseEntity<String> fetchAndSynchronizeWithRaiderApi() {
        return ResponseEntity.ok(
                devienneFouService.fetchAndSynchronizeDatabaseWithRaiderIoApi(
                        raiderIoService.getMembersOfGuildFromRealmInRegion("eu", "Cho'gall", "devienne fou"))
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlayer(@RequestParam Long id){
        return ResponseEntity.ok(
                devienneFouService.deletePlayerById(id)
        );
    }
}
