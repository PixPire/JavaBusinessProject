package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.*;

public interface DiscountDao extends AbstractDao<Discount> {
    public Discount findDiscountByName(String discountName);
}
