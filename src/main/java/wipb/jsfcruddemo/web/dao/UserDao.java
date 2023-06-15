package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.User;

import java.util.Optional;

public interface UserDao extends AbstractDao<User> {
    public Optional<User> findByLogin(String username);
}
