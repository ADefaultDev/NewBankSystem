package com.example.banksystem.GUI;

import com.example.banksystem.client.Client;
import com.example.banksystem.client.ClientRepository;
import com.example.banksystem.credit.Credit;
import com.example.banksystem.deposit.Deposit;
import com.example.banksystem.deposit.DepositRepository;
import com.example.banksystem.deposit.DepositType;
import com.example.banksystem.deposit.DepositTypeRepository;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

@SpringComponent
@UIScope

@SuppressWarnings("unchecked")
public class DepositEditor extends Editor {
    private Deposit deposit;
    ClientRepository clientRepository;


    DepositTypeRepository depositTypeRepository;
    TextField balance = new TextField();

    Select<DepositType> depositTypeSelect = new Select<>();

    Select<Client> clientSelect = new Select<>();
    List<DepositType> depositTypes;
    List<Client> clients;

    Binder<Deposit> binder = new Binder<>(Deposit.class);

    @Autowired
    public DepositEditor(DepositRepository repository, ClientRepository clientRepository,
                         DepositTypeRepository depositTypeRepository, ResourceBundleMessageSource messageSource) {
        super(repository, messageSource);
        this.clientRepository = clientRepository;
        this.depositTypeRepository = depositTypeRepository;

        depositTypes = depositTypeRepository.findAll();
        clients = clientRepository.findAll();
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
        this.deposit.setDepositType(depositTypeSelect.getValue());
        this.deposit.setClient(clientSelect.getValue());
        binder.validate();
        if(binder.isValid()) {
            repository.save(deposit);
            changeHandler.onChange();
        }

    }

   public final void editDeposit(Deposit deposit) {
       depositTypeSelect.setLabel(messageSource.getMessage("ChooseDepositType",null,locale));
       clientSelect.setLabel(messageSource.getMessage("ChooseClient",null, locale));
       balance.setLabel(messageSource.getMessage("balance",null,locale));

       if (deposit == null) {
           setVisible(false);
           return;
       }
       depositTypeSelect.setItems(depositTypes);
       clientSelect.setItems(clients);


       final boolean persisted = deposit.getId() != null;
       if (persisted) {
           this.deposit = (Deposit) repository.findById(deposit.getId()).orElse(null);
       } else {
           this.deposit = deposit;
       }
       int depositTypeId;
       int clientId;
       try {
           depositTypeId = Math.toIntExact(this.deposit.getDepositType().getId());
           clientId = Math.toIntExact(this.deposit.getClient().getId());
       } catch (NullPointerException e) {
           depositTypeId = 1;
           clientId = 2;
       }
       depositTypeSelect.setValue(depositTypes.get(depositTypeId - 1));
       clientSelect.setValue(clients.get(clientId - 2));

       if (persisted) {
           depositTypeSelect.setInvalid(true);
           clientSelect.setInvalid(true);
           int finalDepositTypeId = depositTypeId;
           depositTypeSelect.setItemEnabledProvider(item -> depositTypes.get(finalDepositTypeId - 1).equals(item));
           int finalClientId = clientId;
           clientSelect.setItemEnabledProvider(item -> clients.get(finalClientId - 2).equals(item));
       } else {
           depositTypeSelect.setInvalid(false);
           clientSelect.setInvalid(false);
           depositTypeSelect.setItemEnabledProvider(null);
           clientSelect.setItemEnabledProvider(null);
       }

       binder.setBean(this.deposit);
       binder.forField(balance).withConverter(new StringToLongConverter(messageSource.getMessage("NotANumber",null,locale)))
               .withValidator(balance -> balance > depositTypeSelect.getValue().getMinAmount() &&
                       balance < depositTypeSelect.getValue().getMaxAmount(), messageSource.getMessage("WrongBalance",null,locale))
               .bind(Deposit::getBalance, Deposit::setBalance);
       setVisible(true);
       balance.focus();
   }



}
