package com.example.banksystem.currency;

import javax.persistence.*;

@Entity
@Table(name="currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "denomination", length = 40, nullable = false)
    private String denomination;

    public Currency(){

    }

    public Currency(String denomination) {
        this.denomination = denomination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                '}';
    }
}
