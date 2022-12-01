package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositRepository;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope

@SuppressWarnings("unchecked")
public class DepositEditor extends Editor {
    private Deposit deposit;
    ClientRepository clientRepository;


    DepositTypeRepository depositTypeRepository;
    TextField balance = new TextField("Balance");

    Select<DepositType> depositTypeSelect = new Select<>();

    Select<Client> clientSelect = new Select<>();
    List<DepositType> depositTypes;
    List<Client> clients;

    Binder<Deposit> binder = new Binder<>(Deposit.class);

    @Autowired
    public DepositEditor(DepositRepository repository, ClientRepository clientRepository, DepositTypeRepository depositTypeRepository) {
        super(repository);
        this.clientRepository = clientRepository;
        this.depositTypeRepository = depositTypeRepository;
        depositTypes = depositTypeRepository.findAll();
        clients = clientRepository.findAll();
        depositTypeSelect.setLabel("Choose deposit type:");
        clientSelect.setLabel("Choose the client:");
        add(balance, depositTypeSelect, clientSelect, actions);
        binder.bindInstanceFields(this);
    }
    @Override
    void delete() {
        repository.delete(deposit);
        changeHandler.onChange();
    }
    @Override
    void save() {
        binder.validate();
        this.deposit.setDepositType(depositTypeSelect.getValue());
        this.deposit.setClient(clientSelect.getValue());
        repository.save(deposit);
        changeHandler.onChange();

    }

    public final void editDeposit(Deposit deposit) {
        if (deposit == null) {
            setVisible(false);
            return;
        }
        depositTypeSelect.setItems(depositTypes);
        clientSelect.setItems(clients);


        final boolean persisted = deposit.getId() != null;
        if (persisted) {
            this.deposit = (Deposit) repository.findById(deposit.getId()).orElse(null);
        }
        else {
            this.deposit = deposit;
        }
        int depositTypeId;
        int clientId;
        try {
            depositTypeId = Math.toIntExact(this.deposit.getDepositType().getId());
            clientId = Math.toIntExact(this.deposit.getClient().getId());
        }catch (NullPointerException e){
            depositTypeId = 1;
            clientId=2;
        }
        depositTypeSelect.setValue(depositTypes.get(depositTypeId-1));
        clientSelect.setValue(clients.get(clientId-2));

        binder.setBean(this.deposit);
        setVisible(true);
        balance.focus();



    }
}
