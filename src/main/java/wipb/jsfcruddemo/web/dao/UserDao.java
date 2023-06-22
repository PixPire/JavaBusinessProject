package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.model.UserGroup;

import java.util.Optional;

public interface UserDao extends AbstractDao<User> {
    @Override
    void delete(Long id);
    public Optional<User> findByLogin(String username);
}
