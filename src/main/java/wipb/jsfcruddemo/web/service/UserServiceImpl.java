package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.UserDao;
import wipb.jsfcruddemo.web.model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class UserServiceImpl implements UserService {
    @EJB
    private UserDao userDao;

    @Override
    public User save(User t) { return userDao.save(t); }

    @Override
    public void delete(Long id) { userDao.delete(id); }

    @Override
    public User findById(Long id) { return userDao.findById(id).orElse(null); }
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login).orElse(null);
    }

    @Override
    public boolean verify(String login, String password) {
        User u = userDao.findByLogin(login).orElse(null);
        return u != null ? password.equals(u.getPassword()) : false;

    }
}
