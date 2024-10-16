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

    @GetMapping("/profileDB")
    public ResponseEntity<String> getProfilePlayerFromDB(String region, String realm, String name){
        return ResponseEntity.ok(devienneFouService.getProfile(region,realm,name).toString());
    }

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

    @PostMapping("/synchronizePlayer")
    public ResponseEntity<String> synchronizePlayer(@RequestParam(required = false) String playerName,
                                                    @RequestParam(required = false) Long id) {
        return ResponseEntity.ok(devienneFouService.synchronizePlayerDatabaseWithRaiderIoApi(playerName, id));
    }


    @PostMapping("/synchronize")
    public ResponseEntity<String> synchronizeWithRaiderApi() {
        return ResponseEntity.ok(devienneFouService.synchronizeDatabaseWithRaiderIoApi());
    }

    @PostMapping("/synchronizeDummy")
    public ResponseEntity<String> synchronizeDummy() throws InterruptedException {
        Thread.sleep(10000);
        return ResponseEntity.ok("Players updated");
    }

    @PostMapping("/fetchAndSynchronize")
    public ResponseEntity<String> fetchAndSynchronizeWithRaiderApi() {
        devienneFouService.syncedWithRaiderIoApi();
        return ResponseEntity.ok("Synced task executed successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePlayer(@RequestParam Long id){
        return ResponseEntity.ok(
                devienneFouService.deletePlayerById(id)
        );
    }
}
