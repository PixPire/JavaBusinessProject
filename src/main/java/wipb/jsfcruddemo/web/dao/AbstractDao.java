package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.AbstractModel;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T extends AbstractModel>  {
    T save(T t);
    void delete(Long id);
    Optional<T> findById(Long id);
    List<T> findAll();
}
