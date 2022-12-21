package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;



@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class ClientEditor extends Editor {
    private Client client;

    TextField lastName;
    TextField firstName;
    TextField middleName;
    TextField passportField;

    Binder<Client> binder = new Binder<>(Client.class);

    @Autowired
    public ClientEditor(ClientRepository repository, ResourceBundleMessageSource messageSource) {
        super(repository, messageSource);



        lastName = new TextField();
        firstName = new TextField();
        middleName = new TextField();
        passportField = new TextField();

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

        lastName.setLabel(messageSource.getMessage("LastName",null, locale));
        firstName.setLabel(messageSource.getMessage("FirstName",null, locale));
        middleName.setLabel(messageSource.getMessage("MiddleName",null, locale));
        passportField.setLabel(messageSource.getMessage("passport",null, locale));

        save.setText(messageSource.getMessage("Save",null, locale));
        cancel.setText(messageSource.getMessage("Cancel",null, locale));
        delete.setText(messageSource.getMessage("Delete",null, locale));


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
                .withValidator(passportField -> !passportField.startsWith("0"),messageSource.getMessage("PassportStartsWithZero",null, locale))
                .withValidator(passportField -> passportField.length() == 10, messageSource.getMessage("PassportContainsTenNumbers",null, locale))
                .withValidator(new RegexpValidator(messageSource.getMessage("PassportContainsNumbers",null, locale),"\\d*"))
                .bind(Client::getPassport,Client::setPassport);

        lastName.addValueChangeListener(this::valueChange);
        binder.forField(lastName)
                        .withValidator(lastName -> lastName.chars().allMatch(Character::isLetter),messageSource.getMessage("LastNameContainsOnlyLetters",null, locale))
                        .withValidator(lastName-> lastName.length() > 0,messageSource.getMessage("LastNameContainsOneLetter",null, locale))
                        .bind(Client::getLastname, Client::setLastname);

        firstName.addValueChangeListener(this::valueChange);
        binder.forField(firstName)
                .withValidator(firstName -> firstName.chars().allMatch(Character::isLetter),messageSource.getMessage("FirstNameContainsOnlyLetters",null, locale))
                .withValidator(firstName -> firstName.length() > 0,messageSource.getMessage("FirstNameContainsOneLetter",null, locale))
                .bind(Client::getFirstname, Client::setFirstname);

        middleName.addValueChangeListener(this::valueChange);
        binder.forField(middleName)
                .withValidator(middleName -> middleName.chars().allMatch(Character::isLetter),messageSource.getMessage("MiddleNameContainsOnlyLetters",null, locale))
                .withValidator(middleName -> middleName.length() > 0,messageSource.getMessage("MiddleNameContainsOneLetter",null, locale))
                .bind(Client::getMiddlename, Client::setMiddlename);

        setVisible(true);
        lastName.focus();


    }


}