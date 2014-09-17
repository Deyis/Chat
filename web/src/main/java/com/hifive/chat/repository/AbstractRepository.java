package com.hifive.chat.repository;

import com.hifive.chat.model.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractRepository<M extends BaseModel> implements BaseRepository<M> {

    @PersistenceContext
    protected EntityManager em;

}
