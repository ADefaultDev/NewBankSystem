package com.example.banksystem.credit;

import com.example.banksystem.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.SQLDataException;
import java.time.LocalDate;

@Entity
@Table(name="credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;
    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;
    @Column(name = "creditExpired", nullable = false)
    private String creditExpired;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Client client;


    public Credit() {

    }

    public Credit(CreditType creditType, Long balance) throws SQLDataException {
        this.creditType = creditType;
        if(this.creditType.getMinAmount()>balance || this.creditType.getMaxAmount()<balance){
            throw new SQLDataException("Credit balance is invalid");
        }else{
            this.balance=balance;
        }
        this.creationDate = LocalDate.now();
        this.expirationDate = LocalDate.now().plusDays(creditType.getRepaymentTime());
        setCreditExpired("No");
    }
    public Credit(Long balance) {
        this.balance = balance;
        this.creationDate = LocalDate.now();
        setCreditExpired("No");
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
    public LocalDate getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate){
        this.creationDate = creationDate;
    }
    public LocalDate getExpirationDate(){ return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public String getCreditExpired() {
        return creditExpired;
    }
    public void setCreditExpired(String creditExpired) {
        this.creditExpired = creditExpired;
    }
    @Override
    public String toString() {
        return creditType.getName();
    }

}
