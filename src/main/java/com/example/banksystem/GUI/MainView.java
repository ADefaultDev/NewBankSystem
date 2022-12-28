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
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Route
public class MainView extends VerticalLayout{
    private final DepositEditor depositEditor;
    private final ClientEditor clientEditor;

    private final CreditEditor creditEditor;

    Grid<Client> clientGrid;
    Grid<Credit> creditGrid;
    Grid<CreditType> creditTypeGrid;
    Grid<DepositType> depositTypeGrid;
    Grid<Deposit> depositGrid;
    TextField filter;

    ResourceBundleMessageSource messageSource;
    Locale viewLocale;
    private final Button addNewClientBtn, addNewCreditBtn, addNewDepositBtn;


    public MainView(CreditTypeRepository creditTypeRepository, CreditRepository creditRepository, ClientRepository clientRepository,
                    DepositTypeRepository depositTypeRepository, DepositRepository depositRepository, ClientEditor clientEditor, DepositEditor depositEditor,
                    CreditEditor creditEditor){
        messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("lang/res");
        //Getting locale from browser settings
        viewLocale = VaadinService.getCurrentRequest().getLocale();

        this.depositEditor = depositEditor;
        depositEditor.setLocale(viewLocale);
        this.clientEditor = clientEditor;
        clientEditor.setLocale(viewLocale);
        this.creditEditor = creditEditor;
        creditEditor.setLocale(viewLocale);

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

        this.addNewClientBtn = new Button(( messageSource.getMessage("NewClient",null, viewLocale)), VaadinIcon.PLUS.create());
        this.addNewCreditBtn = new Button(( messageSource.getMessage("NewCredit",null, viewLocale)), VaadinIcon.PLUS.create());
        this.addNewDepositBtn = new Button(( messageSource.getMessage("NewDeposit",null, viewLocale)), VaadinIcon.PLUS.create());



        add(new Button( messageSource.getMessage("ShowClients",null, viewLocale) , event -> showClients(clientRepository)));
        add(new Button( messageSource.getMessage("ShowCredits",null, viewLocale) , event -> showCredits(creditRepository)));
        add(new Button( messageSource.getMessage("ShowDeposits",null, viewLocale) , event -> showDeposits(depositRepository)));
        add(new Button( messageSource.getMessage("ShowCreditTypes",null, viewLocale) , event -> showCreditTypes(creditTypeRepository)));
        add(new Button( messageSource.getMessage("ShowDepositTypes",null, viewLocale) , event -> showDepositTypes(depositTypeRepository)));
        add(new Button( messageSource.getMessage("RemoveAll",null, viewLocale) , event  -> removeAll()));

    }


    public void showClients(ClientRepository clientRepository){
        removeAll();
        buttonConfig(messageSource.getMessage("FilterByName",null, viewLocale));
        filter.addValueChangeListener(event -> clientsFiltering(event.getValue(),clientRepository));

        clientGrid.setItems(clientRepository.findAll());

        localizeClients();
        reorderClients();

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

    public void localizeClients(){
        for (Grid.Column<Client> grid:
                clientGrid.getColumns()) {
            clientGrid.getColumnByKey(grid.getKey()).setHeader(messageSource.getMessage(grid.getKey(),null, viewLocale));
        }
    }

    public void reorderClients(){
        List<String> columnNames = List.of("id","lastname","firstname","middlename","passport","credits","deposits");
        List<Grid.Column<Client>> clientColumnList = new ArrayList<>();
        for (String columnName : columnNames){
            clientColumnList.add(clientGrid.getColumnByKey(columnName));
        }
        clientGrid.setColumnOrder(clientColumnList);
    }

    public void clientsFiltering(String filterText, ClientRepository clientRepository){
        if(!StringUtils.hasText(filterText)){
            clientGrid.setItems(clientRepository.findAll());
        }else {
            clientGrid.setItems(clientRepository.findClientByName(filterText.replace(" ", "")));
        }
    }

    public void showCredits(CreditRepository creditRepository){
        removeAll();
        buttonConfig(messageSource.getMessage("FilterByClientName",null, viewLocale));
        filter.addValueChangeListener(event -> creditsFiltering(event.getValue(), creditRepository));
        creditGrid.setItems(creditRepository.findAll());

        localizeCredits();
        reorderCredits();

        add(addNewCreditBtn, creditGrid, creditEditor);
        creditGrid.asSingleSelect().addValueChangeListener(e -> {
            creditEditor.editCredit(e.getValue());
        });

        addNewCreditBtn.addClickListener(e -> creditEditor.editCredit(new Credit(0L)));


        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            creditsFiltering(filter.getValue(),creditRepository);
        });
    }

    public void reorderCredits(){
        List<String> columnNames = List.of("id","client","balance","creditType","creationDate","expirationDate","creditExpired");
        List<Grid.Column<Credit>> creditColumnList = new ArrayList<>();
        for (String columnName : columnNames){
            creditColumnList.add(creditGrid.getColumnByKey(columnName));
        }
        creditGrid.setColumnOrder(creditColumnList);
    }

    public void localizeCredits(){
        for (Grid.Column<Credit> column:
             creditGrid.getColumns()) {
            creditGrid.getColumnByKey(column.getKey()).setHeader(messageSource.getMessage(column.getKey(),null, viewLocale));
        }
    }

    public void creditsFiltering(String filterText, CreditRepository creditRepository){
        if(!StringUtils.hasText(filterText)){
            creditGrid.setItems(creditRepository.findAll());
        }else {
            creditGrid.setItems(creditRepository.findCreditByClient(filterText.replace(" ", "")));
        }
    }

    public void showCreditTypes(CreditTypeRepository creditTypeRepository){
        removeAll();
        buttonConfig((messageSource.getMessage("FilterByName",null, viewLocale)));
        filter.addValueChangeListener(event -> creditTypeFiltering(event.getValue(), creditTypeRepository));
        creditTypeGrid.setItems(creditTypeRepository.findAll());

        reorderCreditTypes();
        localizeCreditTypes();

        add(creditTypeGrid);
    }

    public void reorderCreditTypes(){
        List<String> columnNames = List.of("id","currency","minAmount","maxAmount","name","rate","repaymentTime");
        List<Grid.Column<CreditType>> creditTypeColumnList = new ArrayList<>();
        for (String columnName : columnNames){
            creditTypeColumnList.add(creditTypeGrid.getColumnByKey(columnName));
        }
        creditTypeGrid.setColumnOrder(creditTypeColumnList);
    }

    public void localizeCreditTypes(){
        for (Grid.Column<CreditType> column:
                creditTypeGrid.getColumns()) {
            creditTypeGrid.getColumnByKey(column.getKey()).setHeader(messageSource.getMessage(column.getKey(),null, viewLocale));
        }
    }

    public void creditTypeFiltering(String filterText, CreditTypeRepository creditTypeRepository){
        if(!StringUtils.hasText(filterText)){
            creditTypeGrid.setItems(creditTypeRepository.findAll());
        }else {
            creditTypeGrid.setItems(creditTypeRepository.findByNameStartsWithIgnoreCase(filterText));
        }
    }

    public void showDeposits(DepositRepository depositRepository){
        removeAll();
        buttonConfig((messageSource.getMessage("FilterByClientName",null, viewLocale)));
        filter.addValueChangeListener(event -> depositsFiltering(event.getValue(),depositRepository));
        depositGrid.setItems(depositRepository.findAll());

        localizeDeposits();
        reorderDeposits();

        add(addNewDepositBtn, depositGrid, depositEditor);

        depositGrid.asSingleSelect().addValueChangeListener(e -> {
            depositEditor.editDeposit(e.getValue());
        });

        addNewDepositBtn.addClickListener(e -> depositEditor.editDeposit(new Deposit(0L)));


        depositEditor.setChangeHandler(() -> {
            depositEditor.setVisible(false);
            depositsFiltering(filter.getValue(),depositRepository);
        });
    }

    public void reorderDeposits(){
        List<String> columnNames = List.of("id","client","balance","depositType","creationDate");
        List<Grid.Column<Deposit>> depositColumnList = new ArrayList<>();
        for (String columnName : columnNames){
            depositColumnList.add(depositGrid.getColumnByKey(columnName));
        }
        depositGrid.setColumnOrder(depositColumnList);
    }

    public void localizeDeposits(){
        for (Grid.Column<Deposit> column:
                depositGrid.getColumns()) {
            depositGrid.getColumnByKey(column.getKey()).setHeader(messageSource.getMessage(column.getKey(),null, viewLocale));
        }
    }

    public void depositsFiltering(String filterText, DepositRepository depositRepository){
        if(!StringUtils.hasText(filterText)){
            depositGrid.setItems(depositRepository.findAll());
        }else {
            depositGrid.setItems(depositRepository.findDepositByClient(filterText.replace(" ", "")));
        }
    }

    public void showDepositTypes(DepositTypeRepository depositTypeRepository){
        removeAll();
        buttonConfig((messageSource.getMessage("FilterByName",null, viewLocale)));
        filter.addValueChangeListener(event -> depositTypesFiltering(event.getValue(),depositTypeRepository));
        depositTypeGrid.setItems(depositTypeRepository.findAll());

        reorderDepositTypes();
        localizeDepositTypes();

        add(depositTypeGrid);
    }

    public void reorderDepositTypes(){
        List<String> columnNames = List.of("id","minAmount","maxAmount","name","rate");
        List<Grid.Column<DepositType>> depositTypeColumnList = new ArrayList<>();
        for (String columnName : columnNames){
            depositTypeColumnList.add(depositTypeGrid.getColumnByKey(columnName));
        }
        depositTypeGrid.setColumnOrder(depositTypeColumnList);
    }

    public void localizeDepositTypes(){
        for (Grid.Column<DepositType> column:
                depositTypeGrid.getColumns()) {
            depositTypeGrid.getColumnByKey(column.getKey()).setHeader(messageSource.getMessage(column.getKey(),null, viewLocale));
        }
    }

    public void depositTypesFiltering(String filterText, DepositTypeRepository depositTypeRepository){
        if(!StringUtils.hasText(filterText)){
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
        this.remove(creditEditor,clientEditor,depositEditor);
        this.remove(filter,clientGrid, creditTypeGrid, creditGrid, depositTypeGrid, depositGrid, addNewClientBtn,addNewCreditBtn, addNewDepositBtn);
    }
}
