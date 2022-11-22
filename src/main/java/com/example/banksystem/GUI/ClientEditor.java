package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


//TODO Abstract Editor for client, credits, deposits entities
@SpringComponent
@UIScope

public class ClientEditor extends VerticalLayout implements KeyNotifier {

    private final ClientRepository repository;

    private Client client;
    TextField lastNameField = new TextField("Last name");
    TextField firstNameField = new TextField("First name");
    TextField middleNameField = new TextField("Middle name");
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
        add(lastNameField, firstNameField, middleNameField, passportField, actions);
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
        if (passportField.getValue().length()==10) {
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
        binder.forField(passportField).withValidator(passportField -> passportField.length() == 10,
                "Passport must contain 10 numbers").bind(Client::getPassport,Client::setPassport);
        setVisible(true);
        lastNameField.focus();
    }

    private void valueChange(HasValue.ValueChangeEvent<String> e) {
        binder.validate();

    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

}