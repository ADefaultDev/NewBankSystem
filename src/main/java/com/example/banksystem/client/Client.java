package com.example.banksystem.client;

import com.example.banksystem.credit.Credit;
import com.example.banksystem.deposit.Deposit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;
    @Column(name = "middlename", length = 50, nullable = false)
    private String middlename;
    @Column(name = "passport", length = 10, nullable = false)
    private String passport;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Credit> credits = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Deposit> deposits = new ArrayList<>();

    public Client(){

    }

    public Client(String name, String firstname, String middlename, String passport, List<Deposit> deposits,
                  List<Credit> credits){
        this.lastname =name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.passport=passport;
        this.deposits=deposits;
        this.credits=credits;
    }

    public Client(String name, String firstname, String middlename, String passport, List<Deposit> deposits){
        this.lastname =name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.passport=passport;
        this.deposits=deposits;
    }


    public Client(String name, String firstname, String middlename, String passport){
        this.lastname =name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.passport=passport;

    }

    public void addCredit(Credit credit){
        this.credits.add(credit);
        credit.setClient(this);
    }
    public void removeCredit(Credit credit){
        this.credits.remove(credit);
        credit.setClient(null);
    }

    public void addDeposit(Deposit deposit){
        this.deposits.add(deposit);
        deposit.setClient(this);
    }
    public void removeDeposit(Deposit deposit){
        this.deposits.remove(deposit);
        deposit.setClient(null);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String surname) {
        this.firstname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String patronymic) {
        this.middlename = patronymic;
    }

    public String getPassport(){return passport;}

    public void setPassport(String passport){this.passport=passport;}

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
