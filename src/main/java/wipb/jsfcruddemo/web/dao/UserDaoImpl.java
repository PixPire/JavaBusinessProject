package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.User;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class UserDaoImpl extends AbstractDaoJpaImpl<User> implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        return findSingle("User.findByLogin","login",login);
    }
}
