package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.BasketService;

public interface BasketDao extends AbstractDao<Basket> {
    Basket findByUser(User user);
    BasketProduct findBasketProduct(Basket basket, Product product);
}
