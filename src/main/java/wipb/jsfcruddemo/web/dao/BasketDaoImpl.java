package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.controller.BasketProductController;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.BasketServiceImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class BasketDaoImpl extends AbstractDaoJpaImpl<Basket> implements BasketDao{
    private static Logger logger = Logger.getLogger(BasketDaoImpl.class.getName());
    @Override
    public Basket findByUser(User user) {
        logger.severe("findByUser wywolal sie z parametrem uzytkownika" + user);
        TypedQuery<Basket> query = em.createNamedQuery( "Basket.findBasketByUser", Basket.class);
        query.setParameter(1, user);
        List<Basket> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public BasketProduct findBasketProduct(Basket basket, Product product) {
        logger.severe("findBasketProduct wywolal sie z koszykiem = " + basket + " i produktem = " + product);
        TypedQuery<BasketProduct> query = em.createNamedQuery( "Basket.findBasketProduct", BasketProduct.class);
        query.setParameter(1, basket);
        query.setParameter(2, product);
        List<BasketProduct> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
