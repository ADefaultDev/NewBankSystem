package com.example.banksystem.client;

import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.deposit.Deposit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository, CreditRepository creditRepository, CreditTypeRepository creditTypeRepository){
        return args -> {
            List<Credit> credits1 = new ArrayList<>();
            List<Credit> credits2 = new ArrayList<>();
            List<Deposit> deposits1 = new ArrayList<>();
            List<Deposit> deposits2 = new ArrayList<>();
            CreditType creditType1 = new CreditType("vigodno", 2.5d, 10000, 10);
            creditTypeRepository.save(creditType1);
            Credit myCred1 = new Credit(creditType1,2l);
            Credit myCred2 = new Credit(creditType1,21l);
            credits1.add(myCred1);
            credits2.add(myCred2);
            Client jo = new Client("Jo","Jonson","Bart","6561341345", credits1, deposits1);
            Client ra = new Client("Ra","Aga","Abdu","1461348365", credits2,deposits2);
            creditRepository.save(myCred1);
            creditRepository.save(myCred2);
            repository.saveAll(List.of(jo,ra));

        };


    }
}
