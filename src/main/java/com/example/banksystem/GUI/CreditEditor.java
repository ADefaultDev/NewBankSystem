package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.credit.CreditRepository;
import com.example.banksystem.credit.CreditType;
import com.example.banksystem.credit.CreditTypeRepository;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@SpringComponent
@UIScope
@SuppressWarnings("unchecked")
public class CreditEditor extends Editor{

    private Credit credit;
    ClientRepository clientRepository;

    CreditTypeRepository creditTypeRepository;
    TextField balance = new TextField();

    Select<CreditType> creditTypeSelect = new Select<>();

    Select<Client> clientSelect = new Select<>();
    List<CreditType> creditTypes;
    List<Client> clients;

    Binder<Credit> binder = new Binder<>(Credit.class);

    @Autowired
    public CreditEditor(CreditRepository repository, ClientRepository clientRepository,
                        CreditTypeRepository creditTypeRepository, ResourceBundleMessageSource messageSource) {
        super(repository, messageSource);
        this.clientRepository = clientRepository;
        this.creditTypeRepository = creditTypeRepository;

        creditTypes = creditTypeRepository.findAll();
        clients = clientRepository.findAll();
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
        this.credit.setCreditType(creditTypeSelect.getValue());
        this.credit.setClient(clientSelect.getValue());
        binder.validate();
        if(binder.isValid()) {
            this.credit.setExpirationDate(LocalDate.now().plusDays(this.credit.getCreditType().getRepaymentTime()));
            repository.save(credit);
            changeHandler.onChange();
        }


    }

    public final void editCredit(Credit credit) {
        creditTypeSelect.setLabel(messageSource.getMessage("ChooseCreditType",null, locale));
        clientSelect.setLabel(messageSource.getMessage("ChooseClient",null, locale));
        balance.setLabel(messageSource.getMessage("balance",null,locale));

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
            creditTypeSelect.setItemEnabledProvider(null);
            clientSelect.setItemEnabledProvider(null);
        }

        binder.setBean(this.credit);
        binder.forField(balance).withConverter(new StringToLongConverter(messageSource.getMessage("NotANumber",null, locale)))
                    .withValidator(balance -> balance > creditTypeSelect.getValue().getMinAmount() &&
                            balance < creditTypeSelect.getValue().getMaxAmount(), messageSource.getMessage("WrongBalance",null, locale))
                    .bind(Credit::getBalance, Credit::setBalance);

        setVisible(true);
        balance.focus();



    }
}
