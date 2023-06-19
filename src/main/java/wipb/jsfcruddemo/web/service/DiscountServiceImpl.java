package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.dao.DiscountDao;
import wipb.jsfcruddemo.web.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class DiscountServiceImpl implements DiscountService {
    private static Logger logger = Logger.getLogger(DiscountServiceImpl.class.getName());

    @EJB
    private DiscountDao discountDao;

    @EJB
    private BasketDao basketDao;

    @Override
    public Discount save(Discount t) {
        return discountDao.save(t);
    }

    @Override
    public void delete(Long id) {
        discountDao.delete(id);
    }

    @Override
    public Discount findById(Long id) {
        return discountDao.findById(id).orElse(null);
    }
    @Override
    public List<Discount> findAll() {
        return discountDao.findAll();
    }

    @Override
    public void addDiscountToBasketProduct(Basket basket, Product product, Discount discount) {
        logger.severe("Wywolal sie serwis addDiscountToBasketProduct");
        logger.severe("Basket = " + basket + " Product = " + product +  " discount = " + discount);
        BasketProduct bp = basketDao.findBasketProduct(basket, product);
        logger.severe("Znaleziono " + bp);
//        if(discount.getBasketProducts().contains(bp))
//            deleteDiscountFromBasketProduct(bp);
        discount.addBasketProduct(bp);
        save(discount);
    }

    @Override
    public void deleteDiscountFromBasketProduct(Basket basket, Product product) {
        BasketProduct basketProduct = basketDao.findBasketProduct(basket, product);
        logger.severe("deleteDiscountFromBasketProduct Znaleziono koszyk: " + basketProduct);
        if(basketProduct != null){
            Discount discount = basketProduct.getDiscount();
            logger.severe("deleteDiscountFromBasketProduct Znaleziono znizke: " + discount);
            if(discount != null){
                discount.removeBasketProduct(basketProduct);
                save(discount);
            }
        }
    }

//    @Override
//    public void deleteDiscountFromBasketProduct(BasketProduct basketProduct, Discount discount) {
//        discount.removeBasketProduct(basketProduct);
//        save(discount);
//    }
}
