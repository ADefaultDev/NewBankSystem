package com.example.banksystem.deposit;

import javax.persistence.*;

@Entity
@Table(name="deposit")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "deposit_type", length = 40, nullable = false)
    private Long depositType;
    @Column(name = "balance", length = 15, nullable = false)
    private Long balance;
    @Column(name = "client", length = 10, nullable = false)
    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepositType() {
        return depositType;
    }

    public void setDepositType(Long depositType) {
        this.depositType = depositType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", depositType=" + depositType +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }
}

