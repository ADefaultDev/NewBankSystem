package com.example.banksystem.GUI;

import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.util.List;

@Route
public class MainView extends VerticalLayout {

    private final CreditRepository creditRepository;
    final Grid<Credit> grid;
    public MainView(CreditRepository creditRepository){
        this.creditRepository = creditRepository;
        this.grid = new Grid<>(Credit.class);
        add(grid);
        add(new Button("Show credits", event -> showCredits()));
    }

    public void showCredits(){
        grid.setItems(creditRepository.findAll());
    }


}
