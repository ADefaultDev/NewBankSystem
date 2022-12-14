package com.example.banksystem;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.currency.CurrencyRepository;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.sql.SQLDataException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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
                                        DepositTypeRepository depositTypeRepository, CreditRepository creditRepository)
    {
        return args -> {
            creationInterface(clientRepository, creditTypeRepository,depositTypeRepository, currencyRepository, creditRepository);


        };
    }

    void creationInterface(ClientRepository clientRepository, CreditTypeRepository creditTypeRepository,
                           DepositTypeRepository depositTypeRepository, CurrencyRepository currencyRepository, CreditRepository creditRepository){
        expirationStatusUpdate(creditRepository);
        while(true) {
            System.out.println("Choose option: ");
            System.out.println("1.Create a client");
            System.out.println("2.Create a credit");
            System.out.println("3.Create a deposit");
            System.out.println("4.Delete a client");
            System.out.println("5.Exit");
            System.out.println("6.Generate random data");

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
            }else if(option == 6){
                System.out.println("Clients generation in progress...");
                for (int i=0;i<500;i++){
                    Client cl = generateRandomClient();
                    clientRepository.save(cl);
                }
                System.out.println("Credits generation in progress...");
                for (int i=0;i<50;i++){
                    generateRandomCredit(creditTypeRepository,clientRepository);
                }
                System.out.println("Deposits generation in progress...");
                for (int i=0;i<50;i++){
                    generateRandomDeposit(depositTypeRepository,clientRepository);
                }
                System.out.println("Random data generation was successful");
            }else {
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
                        try {
                            Credit newCredit = new Credit(creditType, balance);
                            cl.addCredit(newCredit);
                        }catch (SQLDataException e){
                            System.out.println("Invalid balance");
                        }
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

    void expirationStatusUpdate(CreditRepository creditRepository){
        for (Credit credit:
                creditRepository.findAll()) {
            if(credit.getExpirationDate().isEqual(LocalDate.now()) || credit.getExpirationDate().isBefore(LocalDate.now())){
                credit.setCreditExpired("Yes");
                creditRepository.save(credit);
            }
        }
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

    Client generateRandomClient(){
        return new Client(generateRandomName(), generateRandomName(), generateRandomName(),generateRandomPassport());
    }

    void generateRandomCredit(CreditTypeRepository creditTypeRepository, ClientRepository clientRepository){
        Client newClient = clientRepository.findAll().get(getRandomNumber(0,clientRepository.count()));
        CreditType creditType = creditTypeRepository.findAll().get(getRandomNumber(0,creditTypeRepository.count()));
        try {
            Credit newCredit = new Credit(creditType,generateRandomLong(creditType.getMinAmount(), creditType.getMaxAmount()));
            newClient.addCredit(newCredit);
        }catch (SQLDataException e){

        }
        clientRepository.save(newClient);
    }

    void generateRandomDeposit(DepositTypeRepository depositTypeRepository, ClientRepository clientRepository){
        Client newClient = clientRepository.findAll().get(getRandomNumber(0, clientRepository.count()));
        DepositType depositType = depositTypeRepository.findAll().get(getRandomNumber(0,depositTypeRepository.count()));
        try {
            Deposit newDeposit = new Deposit(depositType, generateRandomLong(depositType.getMinAmount(), depositType.getMaxAmount()));
            newClient.addDeposit(newDeposit);
        }catch (SQLDataException e){

        }
        clientRepository.save(newClient);
    }

    String generateRandomPassport(){
        return generateRandomLong(1000000000L, 9999999999L).toString();
    }
    String generateRandomName(){
        int count = getRandomNumber(6,25);
        return StringUtils.capitalize(RandomStringUtils.randomAlphabetic(count).toLowerCase());
    }

    Long generateRandomLong(Long min, Long max){
        return min  + (long)(Math.random() * (max - min));
    }
    public int getRandomNumber(int min, long max){return (int) ((Math.random() * (max - min)) + min);}
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}