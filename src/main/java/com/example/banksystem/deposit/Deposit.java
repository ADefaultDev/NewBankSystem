package com.example.banksystem.deposit;

import com.example.banksystem.client.Client;
import com.example.banksystem.credit.CreditType;

import javax.persistence.*;

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
    private int balance;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Deposit(){

    }

    public Deposit(DepositType depositType, int balance){
        this.depositType = depositType;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", depositType=" + depositType +
                ", balance=" + balance +
                ", clientId=" + client +
                '}';
    }
}

