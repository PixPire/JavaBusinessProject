package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.ArchiveOrder;

import java.util.List;

public interface ArchiveOrderService {
    ArchiveOrder save(ArchiveOrder t);
    void delete (Long id);
    ArchiveOrder findById(Long id);
    List<ArchiveOrder> findAll();
}


