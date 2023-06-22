package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.controller.UserController;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.model.UserGroup;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class UserDaoImpl extends AbstractDaoJpaImpl<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
    @Override
    public void delete(Long id) {
        logger.severe("Nadpisana metoda delete w userDaoImpl");
        User u = em.getReference(User.class,id);
        logger.severe(u.toString());
        logger.severe("Id koszyka" + u.getBasket().getId().toString());
        Basket b = em.getReference(Basket.class, u.getBasket().getId());
        logger.severe(b.toString());
        em.remove(b);
        logger.severe("udalo sie usunac koszyk");
        em.remove(u);
        logger.severe("Wykonalo sie prawidlowo");
    }
    @Override
    public Optional<User> findByLogin(String login) {
        return findSingle("User.findByLogin","login",login);
    }
}
