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
import java.util.Optional;

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


            //Crete credits
            Credit credit1 = new Credit(creditType1,40l);
            Credit credit2 = new Credit(creditType2,510000l);
            Credit credit3 = new Credit(creditType3,2893l);
            Credit credit4 = new Credit(creditType3,32893l);
            jo.addCredit(credit1);
            ra.addCredit(credit2);
            ra.addCredit(credit3);

            //Save clients
            clientRepository.saveAll(List.of(jo,ra,se));

            //Find client and add him credit
            se = clientRepository.findClientByPassport("5411728329").get();
            se.addCredit(credit4);
            clientRepository.save(se);

            //Get client and remove his credit
            ra.removeCredit(credit2);
            clientRepository.save(ra);





        };


    }
}
