package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.BasketProduct;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.model.UserGroup;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class UserDaoImpl extends AbstractDaoJpaImpl<User> implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        return findSingle("User.findByLogin","login",login);
    }
}
