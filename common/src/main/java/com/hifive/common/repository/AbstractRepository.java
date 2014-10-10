package com.hifive.common.repository;

import com.hifive.common.model.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractRepository<M extends BaseModel> implements BaseRepository<M> {

    @PersistenceContext
    protected EntityManager em;

}
