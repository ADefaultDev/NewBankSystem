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
    @Column(name = "credit_type", length = 40, nullable = false)
    private Long creditType;
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;


    public Credit(){

    }

    public Credit(Long creditType, Long balance) {
        this.creditType = creditType;
        this.balance = balance;

    }
}
