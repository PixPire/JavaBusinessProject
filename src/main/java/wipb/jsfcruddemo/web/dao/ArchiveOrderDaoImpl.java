package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.ArchiveOrder;
import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.model.User;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ArchiveOrderDaoImpl extends AbstractDaoJpaImpl<ArchiveOrder> implements ArchiveOrderDao{
    private static Logger logger = Logger.getLogger(BasketDaoImpl.class.getName());

    @Override
    public List<ArchiveOrder> findByUser(User user) {
        logger.severe("findByUser wywolal sie z parametrem uzytkownika" + user);
        TypedQuery<ArchiveOrder> query = em.createNamedQuery( "ArchiveOrder.findOrdersByUser", ArchiveOrder.class);
        query.setParameter(1, user);
        List<ArchiveOrder> result = query.getResultList();
        return result;
    }
}
