package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.*;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    Basket save(Basket t);
    void delete (Long id);
    Basket findById(Long id);
    List<Basket> findAll();

    Basket findByUser(User user);
    void addEditProductToBasket(Basket basket, Product product, BigDecimal numberOfProductsInBasket, Discount discount);
    void deleteProductFromBasket(Basket basket, Product product);
    void realizeOrder(User user, Basket basket,String address,String phone) throws MessagingException;

}


