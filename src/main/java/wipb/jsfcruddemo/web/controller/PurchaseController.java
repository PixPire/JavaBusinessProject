package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.dao.DiscountDao;
import wipb.jsfcruddemo.web.dao.ProductDao;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.BasketService;
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
public class PurchaseController implements Serializable {
    private static Logger logger = Logger.getLogger(PurchaseController.class.getName());
    @EJB
    private BasketService basketService;
    @EJB
    private BasketDao basketDao;
    @EJB
    private ProductDao productDao;
    @Inject
    private UserBean userBean;
    @EJB
    private UserService userService;

    @EJB
    private DiscountDao discountDao;

    private User actualUser;
    private Basket actualBasket;
    private List<BasketProduct> basketProducts;
    @PostConstruct
    private void init() {
        String login = userBean.getLogin();
        actualUser = userService.findByLogin(login);
        actualBasket = basketDao.findByUser(actualUser);
        basketProducts = actualBasket.getBasketProducts();
        logger.severe("BasketProductController zainicjalizowany z " + basketProducts);
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }
    public void setBasketProducts(List<BasketProduct> basketProducts) {
        this.basketProducts = basketProducts;
    }

    public Basket getActualBasket() {
        return actualBasket;
    }
    public void setActualBasket(Basket actualBasket) {
        this.actualBasket = actualBasket;
    }

    public User getActualUser() {
        return actualUser;
    }
    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }

    public void buyBasket(){
        logger.severe("Zakup wywolal sie");
        logger.severe("Uzytkownik: "+actualUser+" zakupil:");
        for (BasketProduct bp: actualBasket.getBasketProducts()) {
            logger.severe(bp.toString());
        }

        /*tu powinno byc wyslanie maila*/

        redirectToThanksPage();
    }

    public void redirectToThanksPage() {
        logger.severe("Wywolanie metody przekierowujacej na strone z podziekowaniem");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            externalContext.redirect(request.getContextPath() + "/clientRestricted/thanksForPurchase.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
