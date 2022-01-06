package com.example.banksystem.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        Optional<Client> clientOpt= clientRepository.findClientByPassport(client.getPassport());
        if(clientOpt.isPresent()){
            throw new IllegalStateException("passport exist");
        }
        clientRepository.save(client);

    }
}

