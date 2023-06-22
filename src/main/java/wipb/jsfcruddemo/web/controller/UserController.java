/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.Configuration;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.dao.UserGroupDao;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.model.UserGroup;
import wipb.jsfcruddemo.web.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserController implements Serializable {
    private static Logger logger = Logger.getLogger(UserController.class.getName());
    @EJB
    private UserService userService;
    private List<User> users;
    private User editedUser;
    private String editedUserPassword = null;
    @EJB
    private BasketDao basketDao;
    @EJB
    private UserGroupDao userGroupDao;
    private UserGroup editedUserGroup;

    @EJB
    private Configuration configuration;

    private List<UserGroup> availableUserGroups;

    @PostConstruct
    private void init() {
        users = userService.findAll();
        availableUserGroups = userGroupDao.findAll();
        editedUserGroup = userGroupDao.findUserGroupByName("ROLE_CLIENT");
    }

    public List<User> getUsers() {
        return users;
    }

    public User getEditedUser() {
        return editedUser;
    }

    public void setEditedUser(User editedUser) {
        this.editedUser = editedUser;
    }

    public void onAddUser() {
        logger.severe("Add user");
        editedUser = new User();
    }

    public void onEditUser(User c) {
        logger.severe("OnEditUser executed");
        editedUser = c;
        editedUserPassword = editedUser.getPassword();
        updateSelectedUserGroup();
    }

    public void onSaveUser() {
        logger.severe("SAVE EXECUTED");

        //logger.severe("Password przed zmiana = " + editedUser.getPassword());
        editedUser.setPassword(configuration.generateHashedPassword(editedUser.getPassword()));
        if(editedUserPassword != null){
            editedUser.setPassword(editedUserPassword);
            editedUserPassword = null;
        }
        //logger.severe("Password po zmiana = " + editedUser.getPassword());


        editedUser.setUserGroup(editedUserGroup);

        if (editedUser.getId() == null) {
            logger.severe("When add user executed");
            users.add(editedUser);
            Basket newBasket = new Basket("Created", editedUser);
            basketDao.save(newBasket);
            logger.severe("Correct executed");
        }
        User saved = userService.save(editedUser);
        users.replaceAll(c-> c != editedUser ? c : saved);
        editedUser = null;
    }

    public void onRemoveUser(User c) {
        logger.severe("REMOVE USER = " + c);
        userService.delete(c.getId());
        users.remove(c);
    }

    public void onCancelUser() {
        users.replaceAll(c-> c != editedUser ? c : userService.findById(editedUser.getId()));
        editedUser = null;
    }

    public UserGroup getEditedUserGroup() {
        return editedUserGroup;
    }

    public void setEditedUserGroup(UserGroup editedUserGroup) {
        this.editedUserGroup = editedUserGroup;
    }

    public List<UserGroup> getAvailableUserGroups() {
        return availableUserGroups;
    }

    public void setAvailableUserGroups(List<UserGroup> availableUserGroups) {
        this.availableUserGroups = availableUserGroups;
    }

    public void updateSelectedUserGroup() {
        // Set the default selected UserGroup
        if (editedUser != null && editedUser.getUserGroup() != null) {
            editedUserGroup = userGroupDao.findUserGroupByName(editedUser.getUserGroup().getName());
        }
    }

}
