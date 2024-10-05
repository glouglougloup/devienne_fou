package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.dto.MemberDTO;
import com.deviennefou.weeklycheck.dto.MythicPlusRunHistoryDTO;
import com.deviennefou.weeklycheck.service.DevienneFouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DevienneFouController {

    @Autowired
    DevienneFouService devienneFouService;



    @GetMapping({"/","/index"})
    public String getMembers(Model model){
        List<MemberDTO> members = devienneFouService.getMembers();
        model.addAttribute("members", members);
        return "indexv2"; // the name of your Thymeleaf template (without the extension)
    }

    @GetMapping("/historyfront")
    public String getHistory(@RequestParam String playerName, Model model) {
        List<MythicPlusRunHistoryDTO> history = devienneFouService.getHistory(playerName, null);
        model.addAttribute("history", history);
        return "fragments/history :: historyFragment"; // Return the Thymeleaf fragment
    }

}
