package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.ArchiveOrder;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.User;

import java.util.List;

public interface ArchiveOrderDao extends AbstractDao<ArchiveOrder>{
    List<ArchiveOrder> findByUser(User user);
    Long findMaxOrderId();
}
