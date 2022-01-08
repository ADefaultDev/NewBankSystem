package com.example.banksystem.credit;

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
    @Column(name = "client", length = 10, nullable = false)
    private Long clientId;
    //



}
