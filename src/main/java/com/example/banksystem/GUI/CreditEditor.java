package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class CreditEditor extends Editor{

    private Credit credit;



    //ListBox<CreditType> creditType = new ListBox<>();

    ListBox<Client> client= new ListBox<>();

    TextField balance = new TextField("Balance");

    Binder<Credit> binder = new Binder<>(Credit.class);

    public CreditEditor(CreditRepository repository, CreditTypeRepository creditTypeRepository, ClientRepository clientRepository) {
        super(repository);
        add(client, balance, actions);
        client.setItems(clientRepository.findAll());
        try {
            client.setValue(credit.getClient());
        }catch (NullPointerException ignored){

        }



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
        repository.save(credit);
        changeHandler.onChange();



    }

    public final void editCredit(Credit credit, CreditTypeRepository creditTypeRepository) {
        if (credit == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = credit.getId() != null;
        if (persisted) {
            this.credit = (Credit) repository.findById(credit.getId()).orElse(null);
        }
        else {
            this.credit = credit;
        }

        binder.setBean(this.credit);



        setVisible(true);
        balance.focus();


    }

    private void valueChange(AbstractField.ComponentValueChangeEvent<ListBox<CreditType>, CreditType> listBoxCreditTypeComponentValueChangeEvent) {
        binder.validate();
    }


}
