package com.deviennefou.weeklycheck.controller;

import com.deviennefou.weeklycheck.dto.MemberDTO;
import com.deviennefou.weeklycheck.dto.MythicPlusRunHistoryDTO;
import com.deviennefou.weeklycheck.service.DevienneFouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class DevienneFouController {

    @Autowired
    DevienneFouService devienneFouService;

    @GetMapping({"/","/index"})
    public String getMembers(Model model){
        List<MemberDTO> members = devienneFouService.getMembers().stream().sorted(Comparator.comparing(MemberDTO::name)).toList();
        model.addAttribute("members", members);
        return "index";
    }

    @GetMapping("/membersFront")
    public String getFilteredMembers(@RequestParam(required = false) String filter, Model model) {
        List<MemberDTO> members = devienneFouService.getMembers().stream()
                .filter(memberDTO -> memberDTO.name().toLowerCase().contains(filter.toLowerCase()))
                .sorted(Comparator.comparing(MemberDTO::name)).toList();
        model.addAttribute("members", members);
        return "fragments/memberTable :: member-table-body";
    }

    @GetMapping("/historyFront")
    public String getHistory(@RequestParam String playerName, Model model) {
        List<MythicPlusRunHistoryDTO> history = devienneFouService.getHistory(playerName, null);
        model.addAttribute("history", history);
        return "fragments/history :: historyFragment"; // Return the Thymeleaf fragment
    }

}
