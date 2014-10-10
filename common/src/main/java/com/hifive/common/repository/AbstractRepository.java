package com.hifive.common.repository;

import com.hifive.common.model.BaseModel;
import com.hifive.common.util.StreamUtil;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AbstractRepository<M extends BaseModel> implements BaseRepository<M> {

    @PersistenceContext
    protected EntityManager em;

    protected M getSingleByNamedQuery(String name, Object... params) {
        return (M) createAndFillQuery(name, params).getSingleResult();
    }

    protected List<M> getListByNamedQuery(String name, Object... params) {
        return createAndFillQuery(name, params).getResultList();
    }

    protected Query createAndFillQuery(String name, Object... params) {
        Query query = em.createNamedQuery(name);
        StreamUtil.<String, Object>getPairStream(params).forEach(a -> query.setParameter(a.getKey(), a.getValue()));
        return query;
    }
}
