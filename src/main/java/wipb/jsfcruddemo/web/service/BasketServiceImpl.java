package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.controller.BasketProductController;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless
public class BasketServiceImpl implements BasketService {
    private static Logger logger = Logger.getLogger(BasketServiceImpl.class.getName());
    @EJB
    private BasketDao basketDao;

    @Override
    public Basket save(Basket t) {
        return basketDao.save(t);
    }

    @Override
    public void delete(Long id) {
        basketDao.delete(id);
    }

    @Override
    public Basket findById(Long id) {
        return basketDao.findById(id).orElse(null);
    }
    @Override
    public List<Basket> findAll() {
        return basketDao.findAll();
    }

    @Override
    public Basket findByUser(User user) {
        return basketDao.findByUser(user);
    }

    @Override
    public void addEditProductToBasket(Basket basket, Product product, BigDecimal numberOfProductsInBasket, Discount discount){
        logger.severe("Wywolal sie serwis addProdutToBasket");
        logger.severe("Basket = " + basket + " Product = " + product + " ilosc = " + numberOfProductsInBasket);
        BasketProduct bp = basketDao.findBasketProduct(basket, product);
        logger.severe("Znaleziono " + bp);
        if(bp != null)
            deleteProductFromBasket(basket, product);
        basket.addProduct(product, numberOfProductsInBasket, discount);
        save(basket);
    }

    @Override
    public void deleteProductFromBasket(Basket basket, Product product) {
        logger.severe("Usuwanie produktu z koszyka");
        basket.removeProduct(product);
        save(basket);
    }

    @Override
    public void clearBasket(Basket basket) {
        basket.clearBasket();
        save(basket);
    }
}
