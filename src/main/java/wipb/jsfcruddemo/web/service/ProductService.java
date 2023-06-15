package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product t);
    void delete (Long id);
    Product findById(Long id);
    List<Product> findAll();
}


