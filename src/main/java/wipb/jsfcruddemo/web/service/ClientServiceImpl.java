/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.ClientDao;
import wipb.jsfcruddemo.web.model.Client;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ClientServiceImpl implements ClientService {
    @EJB
    private ClientDao clientDao;

    @Override
    public Client save(Client t) {
        return clientDao.save(t);
    }

    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }

    @Override
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }
    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

}
