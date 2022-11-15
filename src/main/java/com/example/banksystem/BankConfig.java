package com.example.banksystem;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.currency.CurrencyRepository;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class BankConfig {
    String passport;
    @Autowired
    ApplicationContext context;

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository clientRepository,
                                        CreditTypeRepository creditTypeRepository, CurrencyRepository currencyRepository,
                                        DepositTypeRepository depositTypeRepository)
    {
        return args -> {
            creationInterface(clientRepository, creditTypeRepository,depositTypeRepository, currencyRepository);


        };
    }

    void creationInterface(ClientRepository clientRepository, CreditTypeRepository creditTypeRepository,
                           DepositTypeRepository depositTypeRepository, CurrencyRepository currencyRepository){
        while(true) {
            if(currencyRepository.count()<1){
                //currencyInitialization(currencyRepository);
            }
            if(creditTypeRepository.count()<1){
                //creditTypeInitialization(creditTypeRepository, currencyRepository);
            }
            if(depositTypeRepository.count()<1){
                //depositTypeInitialization(depositTypeRepository);
            }

            System.out.println("Choose one option: ");
            System.out.println("1.Create a client");
            System.out.println("2.Create a credit");
            System.out.println("3.Create a deposit");
            System.out.println("4.Delete a client");
            System.out.println("5.Exit");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            if (option == 1) {
                clientCreation(clientRepository);
            } else if (option == 2) {
                creditCreation(clientRepository, creditTypeRepository);
            } else if (option == 3) {
                depositCreation(clientRepository, depositTypeRepository);
            } else if (option == 4) {
                clientDeletion(clientRepository);
            }else if(option == 5){
                SpringApplication.exit(context);
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }
    }

    void passportEnter(){
        System.out.print("Enter client's passport: ");
        Scanner sc = new Scanner(System.in);
        passport = sc.nextLine();
        String pattern = "[0-9]+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(passport);
        if(passport.length()!=10){
            System.out.println("Passport must be 10 characters");
            passportEnter();
        }else if(!m.matches()){
            System.out.println("Passport must contain only ten numbers");
            passportEnter();
        }else if(passport.split("")[0].equals("0")){
            System.out.println("Passport mustn't start with 0");
            passportEnter();
        }
    }

    String nameEdition(String name){
        name = name.replaceAll(" ", "");
        name = name.toLowerCase();
        return name.split("")[0].toUpperCase() + name.substring(1);
    }

    void clientCreation(ClientRepository clientRepository){
        while(true){
            System.out.println("Do you want to add new client?(Y/N)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if(answer.equals("no") || answer.equals("n")){
                break;
            }else if(answer.equals("yes") || answer.equals("y")){
                Client newClient = new Client();
                System.out.print("Enter client's lastname: ");
                newClient.setLastname(nameEdition(scanner.nextLine()));
                System.out.print("Enter client's firstname: ");
                newClient.setFirstname(nameEdition(scanner.nextLine()));
                System.out.print("Enter client's middlename: ");
                newClient.setMiddlename(nameEdition(scanner.nextLine()));
                passportEnter();
                newClient.setPassport(passport);
                clientRepository.save(newClient);
            }else{
                System.out.println("Invalid answer");
            }
        }
        System.out.println("Client creation complete");
    }

    void creditCreation(ClientRepository clientRepository, CreditTypeRepository creditTypeRepository){
        while (true) {
            System.out.println("Do you want to create new credit?(Y/N)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("no") || answer.equals("n")) {
                break;
            } else if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("Choose the client(enter a number): ");
                for (int i=0;i<clientRepository.count();i++){
                    System.out.println(i + 1 + "." + clientRepository.findAll().get(i));
                }
                int index = scanner.nextInt()-1;
                try {
                    Client cl = clientRepository.findAll().get(index);
                    System.out.println("Choose your credit type: ");
                    for (int i=0;i<creditTypeRepository.count();i++){
                        System.out.println(i + 1 + "." + creditTypeRepository.findAll().get(i));
                    }
                    int ctIndex = scanner.nextInt()-1;
                    try{
                        CreditType creditType = creditTypeRepository.findAll().get(ctIndex);
                        System.out.println("Enter credit's balance: ");
                        long balance=0L;
                        try {
                            balance = scanner.nextLong();
                            if(balance<0){
                                throw new NumberFormatException();
                            }
                        }catch (java.util.InputMismatchException e){
                            System.out.println("Not a number");
                        }catch (NumberFormatException e){
                            System.out.println("Balance must be greater than zero");
                        }
                        Credit newCredit = new Credit();
                        newCredit.setCreditType(creditType);
                        newCredit.setBalance(balance);
                        cl.addCredit(newCredit);
                        clientRepository.saveAll(List.of(cl));
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
        System.out.println("Credit creation complete");
    }

    void depositCreation(ClientRepository clientRepository, DepositTypeRepository depositTypeRepository){
        while (true) {
            System.out.println("Do you want to create new deposit?(Y/N)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("no") || answer.equals("n")) {
                break;
            } else if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("Choose the client(enter a number): ");
                for (int i=0;i<clientRepository.count();i++){
                    System.out.println(i + 1 + "." + clientRepository.findAll().get(i));
                }
                int index = scanner.nextInt()-1;
                try {
                    Client cl = clientRepository.findAll().get(index);
                    System.out.println("Choose your deposit type: ");
                    for (int i=0;i<depositTypeRepository.count();i++){
                        System.out.println(i + 1 + "." + depositTypeRepository.findAll().get(i));
                    }
                    int dtIndex = scanner.nextInt()-1;
                    try{
                        DepositType depositType = depositTypeRepository.findAll().get(dtIndex);
                        System.out.println("Enter deposit's balance: ");
                        long balance=0L;
                        try {
                            balance = scanner.nextLong();
                            if(balance<0){
                                throw new NumberFormatException();
                            }
                        }catch (java.util.InputMismatchException e){
                            System.out.println("Not a number");
                        }catch (NumberFormatException e){
                            System.out.println("Balance must be greater than zero");
                        }
                        Deposit newDeposit = new Deposit();
                        newDeposit.setDepositType(depositType);
                        newDeposit.setBalance(balance);
                        cl.addDeposit(newDeposit);
                        clientRepository.saveAll(List.of(cl));
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
        System.out.println("Deposit creation complete");
    }

    void clientDeletion(ClientRepository clientRepository){
        while(true){
            System.out.println("Do you want to delete client?(Y/N)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine().toLowerCase();
            if(answer.equals("no") || answer.equals("n")){
                break;
            }else if(answer.equals("yes") || answer.equals("y")){
                System.out.println("Choose the client(enter a number): ");
                if(clientRepository.count()==0){
                    System.out.println("No clients in database");
                }else {
                    for (int i = 0; i < clientRepository.count(); i++) {
                        System.out.println(i + 1 + "." + clientRepository.findAll().get(i));
                    }
                    int index = scanner.nextInt() - 1;
                    try {
                        Client client = clientRepository.findAll().get(index);
                        clientRepository.delete(client);
                        System.out.println("Client deleted");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No such client");
                    }
                }
            }else{
                System.out.println("Invalid answer");
            }
        }
        System.out.println("Client deletion complete");
    }
}