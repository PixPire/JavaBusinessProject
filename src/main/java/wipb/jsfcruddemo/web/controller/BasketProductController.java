package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.dao.DiscountDao;
import wipb.jsfcruddemo.web.dao.ProductDao;
import wipb.jsfcruddemo.web.model.*;
import wipb.jsfcruddemo.web.service.BasketService;
import wipb.jsfcruddemo.web.service.BasketServiceImpl;
import wipb.jsfcruddemo.web.service.DiscountService;
import wipb.jsfcruddemo.web.service.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class BasketProductController implements Serializable {
    private static Logger logger = Logger.getLogger(BasketProductController.class.getName());
    @EJB
    private BasketService basketService;
    @EJB
    private BasketDao basketDao;
    @EJB
    private ProductDao productDao;
    private List<BasketProduct> basketProducts;
    private BasketProduct editedBasketProduct;
    private Product clickedProduct;

    private String discountCode;

    private String address;

    private String phone;

    @Inject
    private UserBean userBean;
    @EJB
    private UserService userService;

    @EJB
    private DiscountDao discountDao;

    @EJB
    private DiscountService discountService;

    private User actualUser;
    private Basket actualBasket;

    @PostConstruct
    private void init() {
        logger.severe("Inicjalizacja parametrow w kontrolerze basketProductController");
        String login = userBean.getLogin();
        actualUser = userService.findByLogin(login);
        actualBasket = basketDao.findByUser(actualUser);
        logger.severe("Aktualny uzytkownik = "+ actualUser + "Aktualny koszyk = " + actualBasket);
        basketProducts = actualBasket.getBasketProducts();
        logger.severe("BasketProductController zainicjalizowany z " + basketProducts);

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

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }
    public void setBasketProducts(List<BasketProduct> basketProducts) {
        this.basketProducts = basketProducts;
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
        logger.severe("Kod = " + editedBasketProduct.getDiscount());
    }

    public void onSaveBasketProduct() {
        logger.severe("SAVE WYWOLANE");
        logger.severe("editedBasketProduct = "+editedBasketProduct);

        Basket bp = basketDao.findById(editedBasketProduct.getBasket().getId()).get();
        Product pp = productDao.findById(editedBasketProduct.getProduct().getId()).get();

        basketService.addEditProductToBasket(editedBasketProduct.getBasket(), editedBasketProduct.getProduct(), editedBasketProduct.getNumberOfProductsInBasket(), editedBasketProduct.getDiscount());
        logger.severe("DZIALA onSaveBasketProduct");

        logger.severe("Koszyk = " + bp);
        logger.severe("Produkt = "+ pp);
        discountService.deleteDiscountFromBasketProduct(bp, pp);
        logger.severe("Usunieto znizke z produktu = ");

        Discount d = discountDao.findDiscountByName(discountCode);
        if(d != null){
            logger.severe("Promocja to = " + d);
            //d.addBasketProduct(editedBasketProduct);
            logger.severe("Aktualny uzytkownik = " + actualUser);
            if(!d.getOnlyForVips() || actualUser.getIsVip()){
                discountService.addDiscountToBasketProduct(bp, pp, d);
                logger.severe("Dodano znizke do produktu");
                logger.severe("Znizka = " + d.toString());
            }
        }
        editedBasketProduct = null;
        discountCode = null;
    }

    public void onRemoveProductFromBasket(BasketProduct basketProduct) {
        logger.severe("Wywolalo sie chociaz usuniecie produktu z koszyka");
        basketService.deleteProductFromBasket(basketProduct.getBasket(), basketProduct.getProduct());
    }

    public void clearBasket(){
        logger.severe("Wyczyszczono koszyk");
        basketService.clearBasket(actualBasket);
    }

    public void onCancelBasketProduct() {
        logger.severe("CANCEL WYWOLANE");
        editedBasketProduct = null;
        clickedProduct = null;
        logger.severe("editedBasketProduct = " + editedBasketProduct);
    }
    public void onConfirmRealizeOrder() throws MessagingException {
        logger.severe("REALIZE ORDER WYWOLANE");
        basketService.realizeOrder(actualUser,actualBasket,address,phone);
        basketService.clearBasket(actualBasket);
        redirect("/clientRestricted/thanksForPurchase.xhtml");
    }

    public String getDiscountCode(){
        return discountCode;
    }
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getphone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }
    public String getAddress(){return address;}
    public void setAddress(String address)
    {
        this.address=address;
    }
}
