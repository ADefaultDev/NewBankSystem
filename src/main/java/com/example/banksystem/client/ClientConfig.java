package com.example.banksystem.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ClientConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository){
        return args -> {
            Client oleg = new Client("Oleg","Plyas","Olegovich",6561341345d, 10d);
            Client seva = new Client("Seva","Batyrov","Olegovich", 1483607851d, 10d);
            repository.saveAll(List.of(oleg,seva));
        };


    }
}
