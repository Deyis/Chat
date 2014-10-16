package com.hifive.common.repository;

import com.hifive.common.model.BaseModel;


public interface BaseRepository<M extends BaseModel> {

    M findById(Long id);

    M merge(M model);

    void remove(M model);

    void remove(Long id);
}
