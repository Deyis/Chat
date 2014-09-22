package com.hifive.chat.repository;


import com.hifive.chat.model.Queue;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QueueRepositoryImpl extends AbstractRepository<Queue> implements QueueRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Queue pull(String lang) {
        List<Queue> res =  em.createNamedQuery("Queue.findFirstByLang")
                .setParameter("language", lang).setMaxResults(1).getResultList();
        if (res == null || res.isEmpty()) {
            return null;
        }
        res.get(0).setDeleted(true);
        return res.get(0);
    }

    @Override
    public void push(User user, String lang) {
        List<Queue> res =  em.createNamedQuery("Queue.checkIfExist")
                .setParameter("userId", user.getId()).setParameter("language", lang).setMaxResults(1).getResultList();
        if (!res.isEmpty()) {
            return;
        }
        Queue queue = new Queue();
        queue.setUser(user);
        queue.setLanguage(lang);
        em.merge(queue);
    }

}
