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


    public Credit(){

    }

    public Credit(CreditType creditType, Long balance) {
        this.creditType = creditType;
        this.balance = balance;
    }
}
