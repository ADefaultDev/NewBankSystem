package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.data.binder.Binder;

@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class CreditEditor extends Editor{

    private Credit credit;
    //ListBox<CreditType> creditType = new ListBox<>();

    //TextField client = new TextField("Client");
    TextField balance = new TextField("Balance");

    Binder<Credit> binder = new Binder<>(Credit.class);

    public CreditEditor(CreditRepository repository, CreditTypeRepository creditTypeRepository) {
        super(repository);
        add(balance, actions);
        //creditType.setItems(creditTypeRepository.findAll());
        binder.bindInstanceFields(this);
        //binder.forField(creditType).bind(Credit::getCreditType,Credit::setCreditType);
        //binder.forField(client).bind(credit.getClient().getLastname(),credit.getClient()::setLastname);
        //binder.forField(client).bindReadOnly(credit.getClient().getLastname());
        //binder.forField(balance).withNullRepresentation("").withConverter( new StringToLongConverter("Error")).bind(Credit::getBalance,Credit::setBalance);


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

    public final void editCredit(Credit credit) {
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
