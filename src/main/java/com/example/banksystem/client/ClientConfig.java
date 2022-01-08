package com.example.banksystem.client;

import com.example.banksystem.credit.Credit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository){
        return args -> {
            List<Credit> credits = new ArrayList<>();
            credits.add(new Credit());
            Client jo = new Client("Jo","Jonson","Bart","6561341345");
            Client bo = new Client("Bo","Bonny","Clinton", "1483607851");
            repository.saveAll(List.of(jo, bo));
        };


    }
}
