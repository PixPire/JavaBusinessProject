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
    }

    public void onSaveBasketProduct() {
        logger.severe("JAKIMS CUDEM CHYBA NAWET DZIALA CZESCIOWO");
        if (editedBasketProduct.getId() == null) {
            editedBasketProduct.setBasket(actualBasket);
            editedBasketProduct.setProduct(clickedProduct);
            basketProducts.add(editedBasketProduct);
            logger.severe("editBasketProduct bylo null");
        }
        logger.severe("editBasketProduct bylo " + editedBasketProduct);

        logger.severe("\n\neditedBasketProduct = " + editedBasketProduct);
        logger.severe("\n\nbasket = " + actualBasket);
        logger.severe("\n\nbasketProducts = " + basketProducts);

        Basket saved = basketService.save(editedBasketProduct.getBasket());

        editedBasketProduct = null;

        /*Basket previousBasket = editedBasketProduct.getBasket();
        //Basket editedBasket = basketService.save(previousBasket);

        BasketProduct saved = actualBasket.getBasketProducts().get(editedBasketProduct.getId().intValue());
        basketProducts.replaceAll(c-> c != editedBasketProduct ? c : saved);

        editedBasketProduct = null;*/
    }

    public void onRemoveProductFromBasket(BasketProduct basketProduct) {
        logger.severe("Wywolalo sie chociaz usuniecie produktu z koszyka");
        Basket basket = basketProduct.getBasket();
        basket.removeProduct(basketProduct.getProduct());
        basketProducts.remove(basketProduct);
        basketService.save(basket);
    }

    public void onCancelBasketProduct() {
        logger.severe("CANCEL WYWOLANE");
        /*basketProducts.replaceAll(c -> c.getBasket() != editedBasketProduct.getBasket() ? c : basketService.findById(editedBasketProduct.getBasket().getId()).getBasketProducts().get(editedBasketProduct.getId().intValue()));
        editedBasketProduct = null;*/
        editedBasketProduct = null;
    }

    public String getDiscountCode(){
        return discountCode;
    }
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
}
