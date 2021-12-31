package com.example.banksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {

    @RequestMapping(value = "/bank")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "Bank System") String name, Model model) {
        model.addAttribute("name", name);
        return "bank";
    }

}
