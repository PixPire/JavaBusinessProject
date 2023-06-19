package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    Discount save(Discount t);
    void delete (Long id);
    Discount findById(Long id);
    List<Discount> findAll();

    void addDiscountToBasketProduct(Basket basket, Product product, Discount discount);
    void deleteDiscountFromBasketProduct(Basket basket, Product product);
}


