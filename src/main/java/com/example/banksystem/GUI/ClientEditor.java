package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


//TODO Abstract Editor for client, credits, deposits entities
@SpringComponent
@UIScope

public class ClientEditor extends VerticalLayout implements KeyNotifier {

    private final ClientRepository repository;

    private Client client;
    TextField lastName = new TextField("Last name");
    TextField firstName = new TextField("First name");
    TextField middleName = new TextField("Middle name");
    TextField passportField = new TextField("Passport");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Client> binder = new Binder<>(Client.class);
    private ChangeHandler changeHandler;

    @Autowired
    public ClientEditor(ClientRepository repository) {
        this.repository = repository;
        add(lastName, firstName, middleName, passportField, actions);
        //Bind the fields above to Object in ORM
        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());
        addKeyPressListener(Key.ESCAPE, e -> cancel());

        passportField.setMaxLength(10);

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void delete() {
        repository.delete(client);
        changeHandler.onChange();
    }

    void save() {
        if (passportField.getValue().length()==10
                && !passportField.getValue().startsWith("0")
                && passportField.getValue().matches("[0-9]+")
                && lastName.getValue().chars().allMatch(Character::isLetter)
                && firstName.getValue().chars().allMatch(Character::isLetter)
                && middleName.getValue().chars().allMatch(Character::isLetter))  {
            repository.save(client);
            changeHandler.onChange();
        }

    }

    void cancel(){
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
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
            this.client = repository.findById(client.getId()).get();
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
                        .bind(Client::getLastname, Client::setLastname);

        firstName.addValueChangeListener(this::valueChange);
        binder.forField(firstName)
                .withValidator(firstName -> firstName.chars().allMatch(Character::isLetter),"First name must contain only letters")
                .bind(Client::getFirstname, Client::setFirstname);

        middleName.addValueChangeListener(this::valueChange);
        binder.forField(middleName)
                .withValidator(middleName -> middleName.chars().allMatch(Character::isLetter),"Last name must contain only letters")
                .bind(Client::getMiddlename, Client::setMiddlename);

        setVisible(true);
        lastName.focus();


    }

    private void valueChange(HasValue.ValueChangeEvent<String> e) {
        binder.validate();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

}