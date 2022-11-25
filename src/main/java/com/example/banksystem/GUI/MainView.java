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
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.sql.SQLDataException;

@Route
public class MainView extends VerticalLayout{

    private final ClientEditor clientEditor;
    private final CreditEditor creditEditor;

    Grid<Client> clientGrid;
    Grid<Credit> creditGrid;
    Grid<CreditType> creditTypeGrid;
    Grid<DepositType> depositTypeGrid;
    Grid<Deposit> depositGrid;
    TextField filter;
    private final Button addNewClientBtn, addNewCreditBtn;


    public MainView(CreditTypeRepository creditTypeRepository, CreditRepository creditRepository, ClientRepository clientRepository,
                    DepositTypeRepository depositTypeRepository, DepositRepository depositRepository, ClientEditor clientEditor, CreditEditor creditEditor){
        this.clientEditor = clientEditor;
        this.creditEditor = creditEditor;

        clientGrid = new Grid<>(Client.class);
        clientGrid.setColumnReorderingAllowed(true);
        creditGrid = new Grid<>(Credit.class);
        creditGrid.setColumnReorderingAllowed(true);
        creditTypeGrid = new Grid<>(CreditType.class);
        creditTypeGrid.setColumnReorderingAllowed(true);
        depositGrid = new Grid<>(Deposit.class);
        depositGrid.setColumnReorderingAllowed(true);
        depositTypeGrid = new Grid<>(DepositType.class);
        depositTypeGrid.setColumnReorderingAllowed(true);
        filter = new TextField();

        this.addNewClientBtn = new Button("New client", VaadinIcon.PLUS.create());
        this.addNewCreditBtn = new Button("New credit", VaadinIcon.PLUS.create());



        add(new Button("Show clients" , event -> showClients(clientRepository)));
        add(new Button("Show credits" , event -> showCredits(creditRepository,creditTypeRepository)));
        add(new Button("Show credit types" , event -> showCreditTypes(creditTypeRepository)));
        add(new Button("Show deposits" , event -> showDeposits(depositRepository)));
        add(new Button("Show deposit types" , event -> showDepositTypes(depositTypeRepository)));
        add(new Button("Remove all" , event  -> removeAll()));
    }

    public void showClients(ClientRepository clientRepository){
        removeAll();
        buttonConfig("Filter by name");
        filter.addValueChangeListener(event -> clientsFiltering(event.getValue(),clientRepository));
        clientGrid.setItems(clientRepository.findAll());
        add(addNewClientBtn, clientGrid, clientEditor);

        //Open editor from ClientEditor.class when clicking on client grid
        clientGrid.asSingleSelect().addValueChangeListener(e -> {
            clientEditor.editClient(e.getValue());
        });
        //AddNewClientBtn activating editClient function from Editor if clicked
        addNewClientBtn.addClickListener(e -> clientEditor.editClient(new Client("", "", "", "")));

        //After using editor, changeHandler will remove it and set refreshed data to grid
        clientEditor.setChangeHandler(() -> {
            clientEditor.setVisible(false);
            clientsFiltering(filter.getValue(),clientRepository);
        });

    }

    public void clientsFiltering(String filterText, ClientRepository clientRepository){
        if(StringUtils.isEmpty(filterText)){
            clientGrid.setItems(clientRepository.findAll());
        }else {
            clientGrid.setItems(clientRepository.findClientByName(filterText.replace(" ", "")));
        }
    }

    public void showCredits(CreditRepository creditRepository, CreditTypeRepository creditTypeRepository){
        removeAll();
        buttonConfig("Filter by client name");
        filter.addValueChangeListener(event -> creditsFiltering(event.getValue(), creditRepository));
        creditGrid.setItems(creditRepository.findAll());
        add(addNewCreditBtn, creditGrid,creditEditor);


        creditGrid.asSingleSelect().addValueChangeListener(e -> {
            creditEditor.editCredit(e.getValue());
        });


        addNewCreditBtn.addClickListener(e -> {
            try {
                creditEditor.editCredit(new Credit(creditTypeRepository.getReferenceById(1L),0L));
            } catch (SQLDataException ex) {
                throw new RuntimeException(ex);
            }
        });


        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            creditsFiltering(filter.getValue(),creditRepository);
        });

    }

    public void creditsFiltering(String filterText, CreditRepository creditRepository){
        if(StringUtils.isEmpty(filterText)){
            creditGrid.setItems(creditRepository.findAll());
        }else {
            creditGrid.setItems(creditRepository.findCreditByClient(filterText.replace(" ", "")));
        }
    }

    public void showCreditTypes(CreditTypeRepository creditTypeRepository){
        removeAll();
        buttonConfig("Filter by name");
        filter.addValueChangeListener(event -> creditTypeFiltering(event.getValue(), creditTypeRepository));
        creditTypeGrid.setItems(creditTypeRepository.findAll());
        add(creditTypeGrid);
    }

    public void creditTypeFiltering(String filterText, CreditTypeRepository creditTypeRepository){
        if(StringUtils.isEmpty(filterText)){
            creditTypeGrid.setItems(creditTypeRepository.findAll());
        }else {
            creditTypeGrid.setItems(creditTypeRepository.findByNameStartsWithIgnoreCase(filterText));
        }
    }

    public void showDeposits(DepositRepository depositRepository){
        removeAll();
        buttonConfig("Filter by client name");
        filter.addValueChangeListener(event -> depositsFiltering(event.getValue(),depositRepository));
        depositGrid.setItems(depositRepository.findAll());
        add(depositGrid);
    }

    public void depositsFiltering(String filterText, DepositRepository depositRepository){
        if(StringUtils.isEmpty(filterText)){
            depositGrid.setItems(depositRepository.findAll());
        }else {
            depositGrid.setItems(depositRepository.findDepositByClient(filterText.replace(" ", "")));
        }
    }

    public void showDepositTypes(DepositTypeRepository depositTypeRepository){
        removeAll();
        buttonConfig("Filter by name");
        filter.addValueChangeListener(event -> depositTypesFiltering(event.getValue(),depositTypeRepository));
        depositTypeGrid.setItems(depositTypeRepository.findAll());
        add(depositTypeGrid);
    }

    public void depositTypesFiltering(String filterText, DepositTypeRepository depositTypeRepository){
        if(StringUtils.isEmpty(filterText)){
            depositTypeGrid.setItems(depositTypeRepository.findAll());
        }else {
            depositTypeGrid.setItems(depositTypeRepository.findByNameStartsWithIgnoreCase(filterText));
        }
    }

    public void buttonConfig(String placeholder){
        filter.setPlaceholder(placeholder);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        add(filter);
    }

    public void removeAll(){
        filter.setValue("");
        this.remove(filter,clientGrid, creditTypeGrid, creditGrid, depositTypeGrid, depositGrid, addNewClientBtn,addNewCreditBtn);


    }
}
