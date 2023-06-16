package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.model.Basket;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.BasketService;
import wipb.jsfcruddemo.web.service.ProductService;
import wipb.jsfcruddemo.web.service.UserService;

@Named
@ViewScoped
public class BasketController implements Serializable {
    private static final Logger logger = Logger.getLogger(BasketController.class.getName());
    @Inject
    private UserBean userBean;
    @EJB
    private ProductService productService;
    @EJB
    private BasketService basketService;
    @EJB
    private UserService userService;
    private Basket basket;
    private Product editedProduct;
    //private Basket editedBasket;

    @PostConstruct
    private void init() {
        if (userBean.isLogged()) {
            User currentUser = userService.findByLogin(userBean.getLogin());
            logger.warning("\n\nKURWA Aktualny uzytkownik to"+currentUser.toString()+"\n\n");
            if (currentUser != null) {
                logger.warning("\n\nZNALAZLO KURWE\n\n");
                basket = basketService.findByUser(currentUser);
                logger.warning("\n\nPo serwisie");
                logger.warning("\n\nZNALAZLO basket = "+basket+"\n\n");
            }
            else{
                logger.warning("\n\nNo i caly misterny plan w pizdu\n\n");
            }
        }
        logger.warning("\n\nDobrze przynajmniej sie nie wywalilo\n\n");
    }

    public Basket getBasket() {
        return basket;
    }

    public void onAddProductToBasket(){
        editedProduct = new Product();
    }

    public void onRemoveProductFromBasket(Product p) {
        basketService.deleteProductFromBasket(basket, p);
    }

    public void onSaveProductFromBasket() {
        if (editedProduct.getId() == null) {
            basket.addProduct(editedProduct);
        }

        Product saved = productService.save(editedProduct);
        basket.getProducts().replaceAll(c-> c != editedProduct ? c : saved);

        editedProduct = null;
    }

    public void onCancelProductFromBasket() {
        basket.getProducts().replaceAll(c-> c != editedProduct ? c : productService.findById(editedProduct.getId()));
        editedProduct = null;
    }

    /*public void onAddBasket() {
        editedBasket = new Basket();
    }

    public void onEditBasket(Basket c) {
        editedBasket = c;
    }

    public void onSaveBasket() {
        if (editedBasket.getId() == null) {
            baskets.add(editedBasket);
        }

        Basket saved = basketService.save(editedBasket);
        baskets.replaceAll(c-> c != editedBasket ? c : saved);

        editedBasket = null;
    }

    public void onRemoveBasket(Basket c) {
        basketService.delete(c.getId());
        baskets.remove(c);
    }

    public void onCancelBasket() {
        baskets.replaceAll(c-> c != editedBasket ? c : basketService.findById(editedBasket.getId()));
        editedBasket = null;
    }*/
}
