package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.ArchiveOrderDao;
import wipb.jsfcruddemo.web.model.ArchiveOrder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ArchiveOrderServiceImpl implements ArchiveOrderService {
    @EJB
    private ArchiveOrderDao archiveOrderDao;

    @Override
    public ArchiveOrder save(ArchiveOrder t) {
        return archiveOrderDao.save(t);
    }

    @Override
    public void delete(Long id) {
        archiveOrderDao.delete(id);
    }

    @Override
    public ArchiveOrder findById(Long id) {
        return archiveOrderDao.findById(id).orElse(null);
    }
    @Override
    public List<ArchiveOrder> findAll() {
        return archiveOrderDao.findAll();
    }

}
