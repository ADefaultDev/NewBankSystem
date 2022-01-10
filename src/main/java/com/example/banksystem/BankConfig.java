package com.example.banksystem;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.currency.Currency;
import com.example.banksystem.currency.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BankConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository clientRepository, CreditRepository creditRepository,
                                        CreditTypeRepository creditTypeRepository, CurrencyRepository currencyRepository
                                        ){
        return args -> {
            //Create currency
            Currency rubles = new Currency("Ruble");
            Currency euros = new Currency("Euro");
            Currency dollars = new Currency("USD");
            currencyRepository.saveAll(List.of(rubles,euros,dollars));

            //Create credit types
            CreditType creditType1 = new CreditType("Выгодно", 2.5d, 10000, 10, rubles);
            CreditType creditType2 = new CreditType("GreenCard",5.3d, 5000, 20, dollars);
            CreditType creditType3 = new CreditType("Europe moment", 10d, 7000, 9, euros);
            CreditType creditType4 = new CreditType("Не выгодно", 60d, 50000, 20, rubles);
            creditTypeRepository.saveAll(List.of(creditType1, creditType2, creditType3, creditType4));

            //Create clients
            Client jo = new Client("Jo","Jonson","Bart","6561341345");
            Client ra = new Client("Ra","Aga","Abdu","1461348365");
            Client se = new Client("Se","Va","Oleg","5411728329");
            clientRepository.saveAll(List.of(jo,ra,se));

            //Crete credits
            Credit credit1 = new Credit(creditType1,40l,jo);
            Credit credit2 = new Credit(creditType2,510000l,ra);
            Credit credit3 = new Credit(creditType3,2893l,se);
            creditRepository.saveAll(List.of(credit1,credit2,credit3));

            //Create deposits



        };


    }
}
