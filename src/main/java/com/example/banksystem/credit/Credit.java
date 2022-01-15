package com.example.banksystem.credit;

import com.example.banksystem.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private CreditType creditType;
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Client client;


    public Credit(){

    }

    public Credit(CreditType creditType, Long balance, Client client) {
        this.creditType = creditType;
        this.balance = balance;
        this.client = client;
    }

    public Credit(CreditType creditType, Long balance) {
        this.creditType = creditType;
        this.balance = balance;
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

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", creditType=" + creditType +
                ", balance=" + balance +
                ", client=" + client +
                '}';
    }
}
