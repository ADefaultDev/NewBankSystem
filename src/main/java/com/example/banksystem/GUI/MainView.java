package com.example.banksystem.GUI;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
    public MainView(){
            add(new Button("Hello world", event -> Notification.show("Hello world!",1, Notification.Position.MIDDLE)));
    }
}
