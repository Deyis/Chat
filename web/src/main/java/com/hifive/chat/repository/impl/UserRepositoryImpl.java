package com.hifive.chat.repository.impl;

import com.hifive.chat.model.User;
import com.hifive.chat.repository.AbstractRepository;
import com.hifive.chat.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }
}
