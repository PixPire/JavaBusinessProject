package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.BasketService;
import wipb.jsfcruddemo.web.service.UserService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class BasketProductController implements Serializable {
    private static Logger logger = Logger.getLogger(BasketProductController.class.getName());
    @EJB
    private BasketService basketService;
    @EJB
    private BasketDao basketDao;
    private List<BasketProduct> basketProducts;
    private BasketProduct editedBasketProduct;
    private Product clickedProduct;

    private String discountCode;

    @Inject
    private UserBean userBean;
    @EJB
    private UserService userService;

    private User actualUser;
    private Basket actualBasket;

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

    public BasketProduct getEditedBasketProduct() {
        return editedBasketProduct;
    }

    public void setEditedBasketProduct(BasketProduct editedBasketProduct) {
        this.editedBasketProduct = editedBasketProduct;
    }

    public void onAddBasketProduct(Product product) {
        logger.severe("Produkt = " + product);
        clickedProduct = product;
        logger.severe("Kod znizkowy = " + discountCode);
        editedBasketProduct = new BasketProduct();
        editedBasketProduct.setBasket(actualBasket);
        editedBasketProduct.setProduct(clickedProduct);
    }

    public void onEditBasketProduct(BasketProduct bp) {
        logger.severe("EDIT WYWOLANE: " + bp);
        editedBasketProduct = bp;
        /*if(discountCode != null)
            editedBasketProduct.sp*/
        logger.severe("editedBasketProduct = "+editedBasketProduct);
        logger.severe("Liczba = " + editedBasketProduct.getNumberOfProductsInBasket());
        logger.severe("Kod = " + editedBasketProduct.getSpecialDiscount());
    }

    public void onSaveBasketProduct() {
        logger.severe("SAVE WYWOLANE");
        logger.severe("JAKIMS CUDEM CHYBA NAWET DZIALA CZESCIOWO");
        logger.severe("editedBasketProduct = "+editedBasketProduct);
        basketService.addEditProductToBasket(editedBasketProduct.getBasket(), editedBasketProduct.getProduct(), editedBasketProduct.getNumberOfProductsInBasket(), editedBasketProduct.getSpecialDiscount());
        logger.severe("DZIALA onSaveBasketProduct");
        editedBasketProduct = null;
    }

    public void onRemoveProductFromBasket(BasketProduct basketProduct) {
        logger.severe("Wywolalo sie chociaz usuniecie produktu z koszyka");
        basketService.deleteProductFromBasket(basketProduct.getBasket(), basketProduct.getProduct());
    }

    public void onCancelBasketProduct() {
        logger.severe("CANCEL WYWOLANE");
        editedBasketProduct = null;
        clickedProduct = null;
        logger.severe("editedBasketProduct = " + editedBasketProduct);
    }

    public String getDiscountCode(){
        return discountCode;
    }
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
