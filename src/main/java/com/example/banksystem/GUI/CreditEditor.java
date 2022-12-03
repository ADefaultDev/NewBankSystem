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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class CreditEditor extends Editor{

    private Credit credit;
    ClientRepository clientRepository;


    CreditTypeRepository creditTypeRepository;
    TextField balance = new TextField("Balance");

    Select<CreditType> creditTypeSelect = new Select<>();

    Select<Client> clientSelect = new Select<>();
    List<CreditType> creditTypes;
    List<Client> clients;

    Binder<Credit> binder = new Binder<>(Credit.class);

    @Autowired
    public CreditEditor(CreditRepository repository, ClientRepository clientRepository, CreditTypeRepository creditTypeRepository) {
        super(repository);
        this.clientRepository = clientRepository;
        this.creditTypeRepository = creditTypeRepository;
        creditTypes = creditTypeRepository.findAll();
        clients = clientRepository.findAll();
        creditTypeSelect.setLabel("Choose credit type:");
        clientSelect.setLabel("Choose the client:");
        add(balance, creditTypeSelect, clientSelect, actions);
        binder.bindInstanceFields(this);
    }
    @Override
    void delete() {
        repository.delete(credit);
        changeHandler.onChange();
    }
    @Override
    void save() {
        binder.validate();
        this.credit.setCreditType(creditTypeSelect.getValue());
        this.credit.setClient(clientSelect.getValue());
        repository.save(credit);
        changeHandler.onChange();

    }

    public final void editCredit(Credit credit) {
        if (credit == null) {
            setVisible(false);
            return;
        }
        creditTypeSelect.setItems(creditTypes);
        clientSelect.setItems(clients);


        final boolean persisted = credit.getId() != null;
        if (persisted) {
            this.credit = (Credit) repository.findById(credit.getId()).orElse(null);


        }
        else {
            this.credit = credit;
        }
        int creditTypeId;
        int clientId;
        try {
            creditTypeId = Math.toIntExact(this.credit.getCreditType().getId());
            clientId = Math.toIntExact(this.credit.getClient().getId());
        }catch (NullPointerException e){
            creditTypeId = 1;
            clientId=2;
        }
        creditTypeSelect.setValue(creditTypes.get(creditTypeId-1));
        clientSelect.setValue(clients.get(clientId-2));
        if(persisted){
            creditTypeSelect.setInvalid(true);
            clientSelect.setInvalid(true);
            int finalCreditTypeId = creditTypeId;
            creditTypeSelect.setItemEnabledProvider(item -> creditTypes.get(finalCreditTypeId -1).equals(item));
            int finalClientId = clientId;
            clientSelect.setItemEnabledProvider(item -> clients.get(finalClientId -2).equals(item));
        }else{
            creditTypeSelect.setInvalid(false);
            clientSelect.setInvalid(false);
            creditTypeSelect.setItemEnabledProvider(item -> item.equals(item));
            clientSelect.setItemEnabledProvider(item -> item.equals(item));
        }

        binder.setBean(this.credit);
        setVisible(true);
        balance.focus();



    }
}
