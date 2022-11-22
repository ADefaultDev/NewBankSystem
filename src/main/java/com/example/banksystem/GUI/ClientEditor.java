package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
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
    TextField lastName = new TextField("Last name");
    TextField firstName = new TextField("First name");
    TextField middleName = new TextField("Middle name");
    TextField passport = new TextField("Passport");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Client> binder = new Binder<>(Client.class);
    private ChangeHandler changeHandler;

    @Autowired
    public ClientEditor(ClientRepository repository) {
        this.repository = repository;
        add(lastName, firstName, middleName, passport, actions);
        //Bind the fields above to Object in ORM
        binder.bindInstanceFields(this);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());
        addKeyPressListener(Key.ESCAPE, e -> cancel());

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
        repository.save(client);
        changeHandler.onChange();
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
        cancel.setVisible(persisted);

        binder.setBean(this.client);

        setVisible(true);
        lastName.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

}