package com.example.banksystem.credit;

import com.example.banksystem.currency.Currency;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name="credit_type")
public class CreditType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nationalized
    @Column(name = "name", length = 40, nullable = false)
    private String name;
    @Column(name = "rate", length = 4, nullable = false)
    private double rate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Currency currency;
    @Column(name = "min_amount", length = 8, nullable = false)
    private Long minAmount;
    @Column(name = "max_amount", length = 8, nullable = false)
    private Long maxAmount;
    @Column(name = "repayment_time", length = 8, nullable = false)
    private int repaymentTime;
    //
    public CreditType(){

    }

    public CreditType(String name, double rate, Long minAmount, Long maxAmount, int repaymentTime, Currency currency) {
        this.name = name;
        this.rate = rate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.repaymentTime = repaymentTime;
        this.currency = currency;
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

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(int repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return name;
    }
}
