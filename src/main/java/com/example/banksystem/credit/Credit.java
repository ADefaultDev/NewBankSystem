package com.example.banksystem.credit;

import com.example.banksystem.client.Client;

import javax.persistence.*;

@Entity
@Table(name="credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private CreditType creditType;
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;


    public Credit(){

    }

    public Credit(CreditType creditType, Long balance, Client client) {
        this.creditType = creditType;
        this.balance = balance;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
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
}
