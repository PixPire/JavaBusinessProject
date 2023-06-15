package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.Product;

import javax.ejb.Stateless;

@Stateless
public class ProductDaoImpl extends AbstractDaoJpaImpl<Product> implements ProductDao{
}
