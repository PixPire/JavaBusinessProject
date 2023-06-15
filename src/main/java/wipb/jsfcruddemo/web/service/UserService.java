/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.Client;
import wipb.jsfcruddemo.web.model.User;

import java.util.List;

public interface UserService {
    public User findByLogin(String login);
    public boolean verify(String login, String password);
}
