package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.Discount;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class DiscountDaoImpl extends AbstractDaoJpaImpl<Discount> implements DiscountDao{
    private static Logger logger = Logger.getLogger(DiscountDaoImpl.class.getName());

    @Override
    public Discount findDiscountByName(String discountName) {
        logger.severe("findDiscountByName wywolal sie z discountName = " + discountName);
        TypedQuery<Discount> query = em.createNamedQuery( "Discount.findDiscountByName", Discount.class);
        query.setParameter(1, discountName);
        List<Discount> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
