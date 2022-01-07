package com.example.banksystem.client;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;
    @Column(name = "patronymic", length = 50, nullable = false)
    private String patronymic;
    @Column(name = "balance", nullable = false)
    private double balance;
    @Column(name = "passport", length = 50, nullable = false)
    private String passport;

    /*@Column(name = "values",length = 255, nullable = true)
    @Type(type = "com.example.demo.LongArrayCustomType")
    private Long[] values;
    public Long[] getValues(){
        return values;
    }*/

    public Client(){

    }

    public Client(String name, String surname, String patronymic, String passport, double balance){
        this.name=name;
        this.surname=surname;
        this.patronymic=patronymic;
        this.passport=passport;
        this.balance=balance;
        //this.values=values;
    }


    public Client(Long id, String name, String surname, String patronymic, String passport, double balance){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.patronymic=patronymic;
        this.passport=passport;
        this.balance=balance;
        //this.values=values;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassport(){return passport;}

    public void setPassport(String passport){this.passport=passport;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", balance=" + balance +
                ", passport=" + passport +
                '}';
    }
}
