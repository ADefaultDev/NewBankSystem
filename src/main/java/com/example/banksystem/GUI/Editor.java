package com.example.banksystem.GUI;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;

@SuppressWarnings("rawtypes")
public abstract class Editor extends VerticalLayout implements KeyNotifier {

    JpaRepository repository;
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    ResourceBundleMessageSource messageSource;

    Locale locale;
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<?> binder = new Binder<>(Object.class);

    public ChangeHandler changeHandler;


    @Autowired
    public Editor(JpaRepository repository, ResourceBundleMessageSource messageSource){
        this.repository=repository;
        this.messageSource=messageSource;
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

    public void setLocale(Locale locale){
        this.locale=locale;
        save.setText(messageSource.getMessage("Save",null, locale));
        cancel.setText(messageSource.getMessage("Cancel",null, locale));
        delete.setText(messageSource.getMessage("Delete",null, locale));
    }

    public void valueChange(HasValue.ValueChangeEvent<String> e) {
        binder.validate();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
