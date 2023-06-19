/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.Configuration;
import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.dao.UserDao;
import wipb.jsfcruddemo.web.dao.UserGroupDao;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.UserService;
import wipb.jsfcruddemo.web.util.JSF;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class RegistrationController implements Serializable {

    private static Logger logger = Logger.getLogger(RegistrationController.class.getName());
    @Inject
    private UserBean userBean;

    @EJB
    private UserDao userDao;
    @EJB
    private BasketDao basketDao;
    @EJB
    private UserGroupDao userGroupDao;

    @EJB
    private UserService userService;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    /*@Inject
    private Pbkdf2PasswordHash pbkdf;*/
    @EJB
    private Configuration configuration;

    @Inject @ManagedProperty("#{param.new}")
    private boolean isNew;

    private String login;
    private String password;
    private String email;
    private String confirmedPassword;

    private String userGroupName = "ROLE_CLIENT";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getConfirmedPassword() { return confirmedPassword; }

    public void setConfirmedPassword(String confirmedPassword) { this.confirmedPassword = confirmedPassword; }

    public String getUserGroupName() { return userGroupName; }

    public void setUserGroupName(String userGroupName) { this.userGroupName = userGroupName; }

    public void onRegister() throws IOException {
        logger.severe("onRegister executed");
        logger.severe("userGroupDao = " + userGroupDao);

        User checkUser = userService.findByLogin(login);
        if(checkUser != null){
            JSF.addErrorMessage("Nazwa użytkownika jest zajęta");
            return;
        }

        if (!password.equals(confirmedPassword)) {
            JSF.addErrorMessage("Hasło i powtórzone hasło nie są takie same");
            return;
        }

        logger.severe("Login = " + login + " Email = " + email + " password = " + password + " + confirmedPassword = " + confirmedPassword + " userGroupName = " + userGroupDao.findUserGroupByName(userGroupName));

        User u = new User(login, configuration.generateHashedPassword(password), email, userGroupDao.findUserGroupByName(userGroupName));
        userDao.save(u);
        Basket b = new Basket("Created", u);
        basketDao.save(b);
        JSF.redirect("login.xhtml");
    }
}
