package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.Basket;
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
    @Override
    public Basket findByUser(User user) {
        TypedQuery<Basket> query = em.createNamedQuery( "Basket.findBasketByUser", Basket.class);
        query.setParameter(1, user);
        List<Basket> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
