/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.model.Account;
import wipb.jsfcruddemo.web.model.Client;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import wipb.jsfcruddemo.web.service.ClientService;

@Named
@ViewScoped
public class ClientController implements Serializable {
    @EJB
    private ClientService clientService;
    private List<Client> clients;
    private Client editedClient;
    private Account editedAccount;
    
    @PostConstruct
    private void init() {
        clients = clientService.findAll();
    }

    public List<Client> getClients() {
        return clients;
    }
    
    public Account.Type[] getAccountTypes() {
        return Account.Type.values();
    }

    public Client getEditedClient() {
        return editedClient;
    }

    public void setEditedClient(Client editedClient) {
        this.editedClient = editedClient;
    }

    public Account getEditedAccount() {
        return editedAccount;
    }

    public void setEditedAccount(Account editedAccount) {
        this.editedAccount = editedAccount;
    }
    
    public void onAddClient() {
        editedClient = new Client();
    }
    
    public void onEditClient(Client c) {
        editedClient = c;
    }
    
    public void onSaveClient() {
        if (editedClient.getId() == null) {
            clients.add(editedClient);
        }

        /* Operacja zapisu może spowodować zmianę stanu obiektów, np.: dla nowych obiektów baza nadaje id.
          Jeśli stan obiektu jest aktualizowany z użyciem operacji EntityManager.merge, to obiekt ze zmianami nadanymi przez bazę bedzie zwrócony jako wartość.
          Dlatego po zapisie poprzedni obiekt jest podmieniany na zwrócony przez save.
         */
        Client saved = clientService.save(editedClient);
        clients.replaceAll(c-> c != editedClient ? c : saved);

        editedClient = null;
    }
    
     public void onRemoveClient(Client c) {
        clientService.delete(c.getId());
        clients.remove(c);
    }
    
    public void onCancelClient() {
        /*W czasie edycji użytkownik może zmieniać/dodawać/usuwać konta co powoduje zmiany na liscie kont w obiekcie klienta
          W przypadku anulowania zmian przywracamy stan klienta i jego kont z bazy */
        clients.replaceAll(c-> c != editedClient ? c : clientService.findById(editedClient.getId()));

        editedClient = null;
    }
    
    public void onAddAccount() {
        editedAccount = new Account();
    }
    
    public void onEditAccount(Account a) {
        editedAccount = a;
    }
    
    
    public void onSaveAccount() {
        if (!editedClient.getAccounts().contains(editedAccount)) {
            editedClient.add(editedAccount);
        }
        editedAccount = null;
    }
    
    public void onRemoveAccount(Account a) {
        a.getClient().getAccounts().remove(a);
    }
    
    public void onCancelAccount() {
        editedAccount = null;
    }
}
