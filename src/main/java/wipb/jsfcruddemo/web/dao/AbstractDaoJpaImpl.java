package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.AbstractModel;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class AbstractDaoJpaImpl<T extends AbstractModel> implements AbstractDao<T> {

    @PersistenceContext(unitName = "PU")
    protected EntityManager em;

    private final Class<T> type;

    public AbstractDaoJpaImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T save(T t) {
        if (t.getId() == null) {
            em.persist(t);
            return t;
        }
        return em.merge(t);
    }

    @Override
    public void delete(Long id) {
        T t = em.getReference(type,id);
        em.remove(t);
    }

    @Override
    public Optional<T> findById(Long id) {
        T dto = em.find(type, id);
        return Optional.ofNullable(dto);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public List<T> findList(String queryName, Object ...params) {
        TypedQuery<T> query = em.createNamedQuery(queryName, type);
        if (params.length>0) {
            for (int i = 0; i< params.length-1; i+=2) {
                query.setParameter((String) params[i], params[i+1]);
            }
        }
        List<T> result = query.getResultList();
        return result;
    }

    public Optional<T> findSingle(String queryName, Object ...params) {
        TypedQuery<T> query = em.createNamedQuery(queryName, type);
        try {
            if (params.length>0) {
                for (int i = 0; i < params.length-1; i+=2) {
                    query.setParameter((String) params[i], params[i+1]);
                }
            }
            T e = query.getSingleResult();
            return Optional.of(e);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

}