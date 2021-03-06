package com.hifive.common.repository;

import com.hifive.common.model.BaseModel;
import com.hifive.common.util.StreamUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public abstract class AbstractRepository<M extends BaseModel> implements BaseRepository<M> {

    @PersistenceContext
    protected EntityManager em;

    public M findById(Long id) {
        return em.find(getEntityClass(), id);
    }

    public abstract Class<M> getEntityClass();

    public M merge(M model) {
        model = em.merge(model);
        em.flush();
        return model;
    }

    public void remove(Long id) {
        remove(findById(id));
    }

    public void remove(M model) {
        em.remove(model);
        em.flush();
    }

    protected M getSingleByNamedQuery(String name, Object... params) {
        return (M) createAndFillQuery(name, params).getSingleResult();
    }

    protected List<M> getListByNamedQuery(String name, Object... params) {
        return createAndFillQuery(name, params).getResultList();
    }

    protected void executeUpdateNamedQuery(String name, Object... params) {
        createAndFillQuery(name, params).executeUpdate();
    }

    protected Query createAndFillQuery(String name, Object... params) {
        Query query = em.createNamedQuery(name);
        StreamUtil.<String, Object>getPairStream(params).forEach(a -> query.setParameter(a.getKey(), a.getValue()));
        return query;
    }
}
