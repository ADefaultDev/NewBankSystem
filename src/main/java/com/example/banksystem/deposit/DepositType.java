package com.example.banksystem.deposit;

import javax.persistence.*;

@Entity
@Table(name="deposit_type")
public class DepositType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 40, nullable = false)
    private String name;
    @Column(name = "rate", length = 4, nullable = false)
    private double rate;
    @Column(name = "min_amount", length = 8, nullable = false)
    private int minAmount;

    public DepositType(){

    }

    public DepositType(String name, double rate, int minAmount){
        this.name = name;
        this.rate = rate;
        this.minAmount = minAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    @Override
    public String toString() {
        return "DepositType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", minAmount=" + minAmount +
                '}';
    }
}

