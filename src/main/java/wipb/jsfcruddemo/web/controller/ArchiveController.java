package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.dao.*;
import wipb.jsfcruddemo.web.model.*;
import wipb.jsfcruddemo.web.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class ArchiveController implements Serializable {
    private static Logger logger = Logger.getLogger(ArchiveController.class.getName());

    @Inject
    private UserBean userBean;
    @EJB
    private UserService userService;

    @EJB
    private ArchiveProductDao archiveProductDao;
    @EJB
    private ArchiveOrderDao archiveOrderDao;

    private User actualUser;
    private List<ArchiveOrder> actualOrders;
    /*private ArchiveOrder archiveOrder;

    public ArchiveOrder getarchiveOrder() {
        return archiveOrder;
    }

    public void setAo(ArchiveOrder archiveOrder) {
        this.archiveOrder = archiveOrder;
    }*/

    @PostConstruct
    private void init() {
        logger.severe("Inicjalizacja parametrow w kontrolerze basketProductController");
        String login = userBean.getLogin();
        actualUser = userService.findByLogin(login);
        actualOrders = archiveOrderDao.findByUser(actualUser);//basketDao.findByUser(actualUser);
        //archiveOrder = actualOrders.get(0);
        logger.severe("Aktualny uzytkownik = "+ actualUser + "Aktualne zamowienia:");
        for(ArchiveOrder ao: actualOrders)
            logger.severe(ao.toString());

        if(actualUser == null)
            redirect("/index.xhtml");
    }

    public static void redirect(String path) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            externalContext.redirect(request.getContextPath() + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ArchiveOrder> getActualOrders() {
        return actualOrders;
    }

    public void setActualOrders(List<ArchiveOrder> actualOrders) {
        this.actualOrders = actualOrders;
    }
    public User getActualUser() {
        return actualUser;
    }

    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }
}
