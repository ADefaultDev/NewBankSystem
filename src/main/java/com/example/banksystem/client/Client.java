package com.example.banksystem.client;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;
    @Column(name = "middlename", length = 50, nullable = false)
    private String middlename;
    @Column(name = "passport", length = 10, nullable = false)
    private String passport;

    public Client(){

    }

    public Client(String name, String firstname, String middlename, String passport){
        this.lastname =name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.passport=passport;
    }


    public Client(Long id, String name, String firstname, String middlename, String passport){
        this.id=id;
        this.lastname =name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.passport=passport;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String surname) {
        this.firstname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String patronymic) {
        this.middlename = patronymic;
    }

    public String getPassport(){return passport;}

    public void setPassport(String passport){this.passport=passport;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
