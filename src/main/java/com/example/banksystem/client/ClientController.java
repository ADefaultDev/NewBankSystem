package com.example.banksystem.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping(path = "/BankSystem/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Client> getClients(){
        return clientService.getClients();
    }

//    @GetMapping(path = "/new")
//    public String  newClient(Model model){
//        model.addAttribute("client", new Client());
//        return "client/new";
//    }
//
//    @PostMapping
//    public String addClient(@ModelAttribute("client") Client client) {
//        clientService.addNewClient(client);
//        return "redirect:/BankSystem/client/list";
//    }

    @PostMapping
    @ResponseBody
    public void addClientRest(@RequestBody Client client){
        clientService.addNewClient(client);
    }


    @DeleteMapping(path = "{clientId}")
    public void deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
    }

    @PutMapping(path = "{clientId}")
    public void updateClient(@PathVariable("clientId") Long clientId, @RequestParam(required = false) String name, @RequestParam(required = false) String passport) {
        clientService.updateClient(clientId, name, passport);
    }

}
