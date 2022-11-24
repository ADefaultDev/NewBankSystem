package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class Editor extends VerticalLayout implements KeyNotifier {

    JpaRepository repository;
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<?> binder = new Binder<>(Object.class);

    private ClientEditor.ChangeHandler changeHandler;


    @Autowired
    public Editor(JpaRepository repository){
        this.repository = repository;
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());
        addKeyPressListener(Key.ESCAPE, e -> cancel());


        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }


    void delete() {}

    void save() {}

    void cancel(){
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    private void valueChange(HasValue.ValueChangeEvent<String> e) {
        binder.validate();
    }

    public void setChangeHandler(ClientEditor.ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}