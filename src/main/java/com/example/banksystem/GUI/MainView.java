package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositRepository;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.util.List;

@Route
public class MainView extends VerticalLayout {
    private final CreditRepository creditRepository;
    private final CreditTypeRepository creditTypeRepository;
    private final ClientRepository clientRepository;
    private final DepositTypeRepository depositTypeRepository;
    private final DepositRepository depositRepository;

    Grid<DepositType> grid;
    Grid<Credit> creditGrid;
    Grid<CreditType> creditTypeGrid;
    Grid<Client> clientGrid;
    Grid<DepositType> depositTypeGrid;
    Grid<Deposit> depositGrid;
    public MainView(CreditTypeRepository creditTypeRepository, CreditRepository creditRepository, ClientRepository clientRepository,
                    DepositTypeRepository depositTypeRepository, DepositRepository depositRepository){
        this.creditRepository = creditRepository;
        this.creditTypeRepository = creditTypeRepository;
        this.clientRepository = clientRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.depositRepository = depositRepository;

        this.creditGrid = new Grid<>(Credit.class);
        this.creditTypeGrid = new Grid<>(CreditType.class);
        this.clientGrid = new Grid<>(Client.class);
        this.depositTypeGrid = new Grid<>(DepositType.class);
        this.depositGrid = new Grid<>(Deposit.class);


        add(new Button("Show credits" , event -> showTable(creditGrid,creditRepository)));
        add(new Button("Show credit types" , event -> showTable(creditTypeGrid,creditTypeRepository)));
        add(new Button("Show clients" , event -> showTable(clientGrid,clientRepository)));
        add(new Button("Show deposit types" , event -> showTable(depositTypeGrid,depositTypeRepository)));
        add(new Button("Show deposits" , event -> showTable(depositGrid,depositRepository)));
        add(new Button("Remove all" , event  -> remove()));


    }

    public void showTable(Grid<?> grid, JpaRepository jpaRepository){
        this.remove(creditGrid,clientGrid,creditTypeGrid, depositTypeGrid, depositGrid);
        add(grid);
        grid.setItems(jpaRepository.findAll());

    }

    public void remove(){
        this.remove(creditGrid,clientGrid, creditTypeGrid,depositGrid,depositTypeGrid);
    }
    public void showDeposits(){
        grid.setItems(depositTypeRepository.findAll());
    }





}
