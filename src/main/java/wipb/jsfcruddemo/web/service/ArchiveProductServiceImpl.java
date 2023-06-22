package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.ArchiveProductDao;
import wipb.jsfcruddemo.web.model.ArchiveProduct;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ArchiveProductServiceImpl implements ArchiveProductService {
    @EJB
    private ArchiveProductDao ArchiveproductDao;

    @Override
    public ArchiveProduct save(ArchiveProduct t) {
        return ArchiveproductDao.save(t);
    }

    @Override
    public void delete(Long id) {
        ArchiveproductDao.delete(id);
    }

    @Override
    public ArchiveProduct findById(Long id) {
        return ArchiveproductDao.findById(id).orElse(null);
    }
    @Override
    public List<ArchiveProduct> findAll() {
        return ArchiveproductDao.findAll();
    }

}
