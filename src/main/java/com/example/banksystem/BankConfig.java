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
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class BankConfig {
    String passport;

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository clientRepository,
                                        CreditTypeRepository creditTypeRepository, CurrencyRepository currencyRepository,
                                        DepositTypeRepository depositTypeRepository)
    {
        return args -> {
            creationInterface(clientRepository, creditTypeRepository,depositTypeRepository);

            /*//Create currency
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
            depositTypes.addAll(List.of(depositType1,depositType2,depositType3));
            depositTypeRepository.saveAll(depositTypes);

            creationInterface(clientRepository);
            //Create clients
            Client jo = new Client("Jo","Jonson","Bart","6561341345");
            Client ra = new Client("Ra","Aga","Abdu","1461348365");
            Client se = new Client("Se333","Vara","Oleg","5411728329");


            //Crete credits
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
            Deposit deposit1 = new Deposit(depositType1, 550000L);
            Deposit deposit2 = new Deposit(depositType2, 450000L);

            ra.addDeposit(deposit1);
            se.addDeposit(deposit2);
            //clientRepository.saveAll(List.of(ra,se));*/
        };
    }



    void creationInterface(ClientRepository clientRepository, CreditTypeRepository creditTypeRepository,
                           DepositTypeRepository depositTypeRepository){
        while(true) {
            System.out.println("Choose one option: ");
            System.out.println("1.Create a client");
            System.out.println("2.Create a credit");
            System.out.println("3.Create a deposit");
            System.out.println("4.Exit");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            if (option == 1) {
                clientCreation(clientRepository);
            } else if (option == 2) {
                creditCreation(clientRepository, creditTypeRepository);
            } else if (option == 3) {
                depositCreation(clientRepository, depositTypeRepository);
            } else if (option == 4) {
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
                ArrayList<Client> clients = new ArrayList<>();
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
                    long ctIndex = scanner.nextInt()-1;
                    try{
                        CreditType creditType = creditTypeRepository.getById(ctIndex);
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
                        clientRepository.delete(cl);
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
                        clientRepository.delete(cl);
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
}
