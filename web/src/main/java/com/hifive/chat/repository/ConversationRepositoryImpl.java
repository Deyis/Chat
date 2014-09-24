package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ConversationRepositoryImpl extends AbstractRepository<Conversation> implements ConversationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Conversation findById(Long id) {
        return em.find(Conversation.class, id);
    }

    @Override
    public Conversation create(User firstUser, User secondUser, String language) {
        Conversation conversation = new Conversation();
        conversation.setFirstUser(firstUser);
        conversation.setSecondUser(secondUser);
        conversation.setLanguage(language);
        conversation = em.merge(conversation);
        em.flush();
        return conversation;
    }

    @Override
    public Conversation findFreeConversationAndJoin(String lang, User user) {
        List<Conversation> res =  em.createNamedQuery("Conversation.findFreeConversation")
                .setParameter("language", lang).setParameter("currentUserId", user.getId()).getResultList();

        if (res == null || res.isEmpty()) {
            return null;
        }

        res.get(0).setSecondUser(user);
        em.flush();
        return res.get(0);
    }
}
