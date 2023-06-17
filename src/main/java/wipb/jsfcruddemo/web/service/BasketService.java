package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    Basket save(Basket t);
    void delete (Long id);
    Basket findById(Long id);
    List<Basket> findAll();

    Basket findByUser(User user);
    void addProductToBasket(Basket basket, Product product, BigDecimal numberOfProductsInBasket, BigDecimal specialDiscount);
    void deleteProductFromBasket(Basket basket, Product product);
}


