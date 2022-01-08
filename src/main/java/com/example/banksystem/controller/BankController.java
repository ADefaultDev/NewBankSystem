package com.example.banksystem.controller;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/BankSystem/bank")
public class BankController {

    private final ClientService clientService;

    @Autowired
    public BankController(ClientService clientService){
        this.clientService=clientService;
    }

    @RequestMapping(value = "")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "Bank System") String name, Model model) {
        model.addAttribute("name", name);
        return "bank";
    }


}
