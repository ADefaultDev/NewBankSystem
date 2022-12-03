package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class ClientEditor extends Editor {
    private Client client;
    TextField lastName = new TextField("Last name");
    TextField firstName = new TextField("First name");
    TextField middleName = new TextField("Middle name");
    TextField passportField = new TextField("Passport");

    Binder<Client> binder = new Binder<>(Client.class);

    @Autowired
    public ClientEditor(ClientRepository repository) {
        super(repository);
        add(lastName, firstName, middleName, passportField, actions);

        binder.bindInstanceFields(this);

        passportField.setMaxLength(10);

    }
    @Override
    void delete() {
        repository.delete(client);
        changeHandler.onChange();
    }
    @Override
    void save() {
        binder.validate();
        if (binder.isValid()) {
            repository.save(client);
            changeHandler.onChange();
        }


    }

    public final void editClient(Client client) {
        if (client == null) {
            setVisible(false);
            return;
        }
        //If client already exists in repository, edit
        //Else: effectively creating new client
        final boolean persisted = client.getId() != null;
        if (persisted) {
            this.client = (Client) repository.findById(client.getId()).orElse(null);
        }
        else {
            this.client = client;
        }
        binder.setBean(this.client);
        passportField.addValueChangeListener(this::valueChange);
        binder.forField(passportField)
                .withValidator(passportField -> !passportField.startsWith("0"),"Passport mustn't start with 0")
                .withValidator(passportField -> passportField.length() == 10, "Passport must contain 10 numbers")
                .withValidator(new RegexpValidator("Passport must contain only numbers","\\d*"))
                .bind(Client::getPassport,Client::setPassport);

        lastName.addValueChangeListener(this::valueChange);
        binder.forField(lastName)
                        .withValidator(lastName -> lastName.chars().allMatch(Character::isLetter),"Last name must contain only letters")
                        .withValidator(lastName-> lastName.length() > 0,"Last name must contain at least one letter")
                        .bind(Client::getLastname, Client::setLastname);

        firstName.addValueChangeListener(this::valueChange);
        binder.forField(firstName)
                .withValidator(firstName -> firstName.chars().allMatch(Character::isLetter),"First name must contain only letters")
                .withValidator(firstName -> firstName.length() > 0,"First name must contain at least one letter")
                .bind(Client::getFirstname, Client::setFirstname);

        middleName.addValueChangeListener(this::valueChange);
        binder.forField(middleName)
                .withValidator(middleName -> middleName.chars().allMatch(Character::isLetter),"Last name must contain only letters")
                .withValidator(middleName -> middleName.length() > 0,"Last name must contain at least one letter")
                .bind(Client::getMiddlename, Client::setMiddlename);

        setVisible(true);
        lastName.focus();


    }


}