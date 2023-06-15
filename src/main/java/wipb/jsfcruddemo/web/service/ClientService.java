/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.Client;

import java.util.List;

public interface ClientService {
    Client save(Client t);
    void delete (Long id);
    Client findById(Long id);
    List<Client> findAll();
}
