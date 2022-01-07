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
            Client jo = new Client("Jo","Jonson","Bart","6561341345", 10d);
            Client bo = new Client("Bo","Bonny","Clinton", "1483607851", 10d);
            repository.saveAll(List.of(jo, bo));
        };


    }
}
