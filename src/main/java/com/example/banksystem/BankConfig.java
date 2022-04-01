package com.example.banksystem;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.currency.Currency;
import com.example.banksystem.currency.CurrencyRepository;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class BankConfig {

    List<CreditType> creditTypes = new ArrayList<>();
    List<Client> clients = new ArrayList<>();

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository clientRepository,
                                        CreditTypeRepository creditTypeRepository, CurrencyRepository currencyRepository,
                                        DepositTypeRepository depositTypeRepository
    ){
        return args -> {

            //Create currency
            Currency rubles = new Currency("Ruble");
            Currency euros = new Currency("Euro");
            Currency dollars = new Currency("USD");
            currencyRepository.saveAll(List.of(rubles,euros,dollars));

            //Create credit types
            CreditType creditType1 = new CreditType("Выгодно", 2.5d, 10000, 1000000, 10, rubles);
            CreditType creditType2 = new CreditType("GreenCard",5.3d, 5000, 1000000, 20, dollars);
            CreditType creditType3 = new CreditType("Europe moment", 10d, 7000, 1000000, 9, euros);
            CreditType creditType4 = new CreditType("Не выгодно", 60d, 50000, 3000000, 20, rubles);
            creditTypes.addAll(List.of(creditType1,creditType2,creditType3,creditType4));
            creditTypeRepository.saveAll(creditTypes);

            //creditTypes.addAll(creditTypeRepository.findAll());

            //Create deposits types
            DepositType depositType1 = new DepositType("Снежок", 1.2d, 300000, 600000);
            DepositType depositType2 = new DepositType("Little deposit", 1.5d, 400000, 8000000);
            DepositType depositType3 = new DepositType("Вклад двойной", 3d, 1000000, 2000000);
            depositTypeRepository.saveAll(List.of(depositType1,depositType2,depositType3));

            //Create clients
            clientCreation();
            Client jo = new Client("Jo","Jonson","Bart","6561341345");
            Client ra = new Client("Ra","Aga","Abdu","1461348365");
            Client se = new Client("Se333","Vara","Oleg","5411728329");


            //Crete credits
            creditCreation();
            Credit credit1 = new Credit(creditType1, 403333L);
            Credit credit2 = new Credit(creditType2, 510000L);
            Credit credit3 = new Credit(creditType3, 289333L);
            Credit credit4 = new Credit(creditType3,62895L);

            //Find client and add him credit
            jo.addCredit(credit1);
            ra.addCredit(credit2);
            ra.addCredit(credit3);
            se.addCredit(credit4);
            se.addCredit(credit4);

            //Save clients
            clientRepository.saveAll(clients);
            //clientRepository.saveAll(List.of(jo,ra,se));


            //Create deposits
            Deposit deposit1 = new Deposit(depositType1, 550000);
            Deposit deposit2 = new Deposit(depositType2, 450000);

            ra.addDeposit(deposit1);
            se.addDeposit(deposit2);
            //clientRepository.saveAll(List.of(ra,se));



        };
    }

    @Bean
    void clientCreation(){
        while(true){
            System.out.println("Do you want to add new client?");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if(answer.equals("no") || answer.equals("n")){
                break;
            }else if(answer.equals("yes") || answer.equals("y")){
                Client newClient = new Client();
                System.out.print("Enter client's lastname: ");
                newClient.setLastname(scanner.nextLine());
                System.out.print("Enter client's firstname: ");
                newClient.setFirstname(scanner.nextLine());
                System.out.print("Enter client's middlename: ");
                newClient.setMiddlename(scanner.nextLine());
                System.out.print("Enter client's passport: ");
                newClient.setPassport(scanner.nextLine());
                clients.add(newClient);
            }else{
                System.out.println("Invalid answer");
            }
        }
        System.out.println("Client creation complete");
    }


    void creditCreation(){
        while (true) {
            System.out.println("Do you want to create new credit?");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("no") || answer.equals("n")) {
                break;
            } else if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("Choose the client(enter a number): ");
                for (int i=0;i<clients.size();i++){
                    System.out.println(i + "." + clients.get(i).toString());
                }
                int index = scanner.nextInt();
                try {
                    System.out.println("Choose your credit type: ");
                    for (int i=0;i<creditTypes.size();i++){
                        System.out.println(i + "." + creditTypes.get(i).toString());
                    }
                    int ctIndex = scanner.nextInt();
                    try{
                        CreditType creditType = creditTypes.get(ctIndex);
                        System.out.println("Enter credit's balance: ");
                        Long balance = scanner.nextLong();
                        Credit newCredit = new Credit();
                        newCredit.setCreditType(creditType);
                        newCredit.setBalance(balance);
                        newCredit.setClient(clients.get(index));
                        clients.get(index).setCredits(List.of(newCredit));
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("No such credit type");
                    }

                }catch (IndexOutOfBoundsException e){
                    System.out.println("No such client");
                }

            } else {
                System.out.println("Invalid answer");
            }
        }
    }
}
