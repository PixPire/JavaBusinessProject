package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.ArchiveProduct;

import java.util.List;

public interface ArchiveProductService {
    ArchiveProduct save(ArchiveProduct t);
    void delete (Long id);
    ArchiveProduct findById(Long id);
    List<ArchiveProduct> findAll();
}


