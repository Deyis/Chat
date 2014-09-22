package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ConversationRepositoryImpl extends AbstractRepository<Conversation> implements ConversationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Conversation create(User firstUser, User secondUser, String language) {
        Conversation conversation = new Conversation();
        conversation.setFirstUser(firstUser);
        conversation.setSecondUser(secondUser);
        conversation.setLanguage(language);
        return em.merge(conversation);
    }
}
