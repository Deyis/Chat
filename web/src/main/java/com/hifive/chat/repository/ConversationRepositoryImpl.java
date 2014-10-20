package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.repository.AbstractRepository;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class ConversationRepositoryImpl extends AbstractRepository<Conversation> implements ConversationRepository {


    @Override
    public Conversation create(User firstUser, User secondUser, String language) {
        Conversation conversation = new Conversation();
        conversation.setFirstUser(firstUser);
        conversation.setSecondUser(secondUser);
        conversation.setLanguage(language);
        conversation.setLastMessageNumber(new Long(0));
        return merge(conversation);
    }

    @Override
    public Conversation findFreeConversationAndJoin(String lang, User user) {
        List<Conversation> res = getListByNamedQuery("Conversation.findFreeConversation",
                "language", lang,
                "currentUserId", user.getId());

        if (res == null || res.isEmpty()) {
            return null;
        }

        res.get(0).setSecondUser(user);
        em.flush();
        return res.get(0);
    }

    @Override
    public long getLastMessageNumber(long conversationId) {
        return findById(conversationId).getLastMessageNumber();
    }

    @Override
    public Class<Conversation> getEntityClass() {
        return Conversation.class;
    }
}
