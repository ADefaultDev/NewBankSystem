package com.example.banksystem.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        Optional<Client> clientOpt = clientRepository.findClientByPassport(client.getPassport());
        if(clientOpt.isPresent()){
            throw new IllegalStateException("passport exist");
        }
        clientRepository.save(client);

    }

    public void deleteClient(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("client with id " + clientId + " does not exist");
        }
        clientRepository.deleteById(clientId);
    }

    @Transactional
    public void updateClient(Long clientId, String name, String passport) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalStateException("client with id " + clientId + "does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(client.getName(), name)) {
            client.setName(name);
        }

        if (passport != null && passport.length() > 0 && !Objects.equals(client.getPassport(), passport)) {
            Optional<Client> clientOpt = clientRepository.findClientByPassport(passport);
            if(clientOpt.isPresent()){
                throw new IllegalStateException("passport exist");
            }
            client.setPassport(passport);
        }
    }
}

