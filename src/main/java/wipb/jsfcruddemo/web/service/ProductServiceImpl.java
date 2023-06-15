package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.ProductDao;
import wipb.jsfcruddemo.web.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductServiceImpl implements ProductService {
    @EJB
    private ProductDao productDao;

    @Override
    public Product save(Product t) {
        return productDao.save(t);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }
    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

}
