package com.example.banksystem.deposit;

import com.example.banksystem.client.Client;
import com.example.banksystem.credit.CreditType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.SQLDataException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="deposit")

public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private DepositType depositType;
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @Column(name = "creationDate", nullable = false)
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Client client;

    public Deposit(){

    }

    public Deposit(Client client, DepositType depositType, Long balance) {
        this.client = client;
        this.depositType = depositType;
        this.balance=balance;
    }


    public Deposit(DepositType depositType, Long balance) throws SQLDataException {
        this.depositType = depositType;
        if(this.depositType.getMinAmount()>balance || this.depositType.getMaxAmount()<balance){
            throw new SQLDataException("Deposit balance is invalid");
        }else{
            this.balance=balance;
        }
        this.date = LocalDate.now();
    }

    public Deposit(long balance) {
        this.balance=balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    @Override
    public String toString() {
        return depositType.getName();
    }
}

