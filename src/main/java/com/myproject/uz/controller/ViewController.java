package com.myproject.uz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class ViewController {
    @GetMapping("/cabinet")
    public String cabinet(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "cabinet";
    }

    @GetMapping("/chat/{chatId}")
    public String chat(@PathVariable Long chatId, Model model, Principal principal) {
        model.addAttribute("chatId", chatId);
        model.addAttribute("username", principal.getName());
        return "chat";
    }
}
