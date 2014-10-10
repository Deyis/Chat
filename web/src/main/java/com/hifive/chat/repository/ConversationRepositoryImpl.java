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
        conversation.setLastMessageNumber(new Long(0));
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

    @Override
    public void addMessageToConversation(Conversation conversation, String message, User user) {
//        Conversation conversation = em.find(Conversation.class, conversationId);
        Long lastNumber = conversation.getLastMessageNumber() + 1;
        conversation.setLastMessageNumber(lastNumber);
        Message newMessage = new Message();
        newMessage.setUser(user);
        newMessage.setMessage(message);
        newMessage.setConversation(conversation);
        newMessage.setMessageNumber(lastNumber);
        newMessage.setCreationDate(new Date());
        em.merge(newMessage);
        em.flush();
    }

    @Override
    public List<Message> getMessagesForConversation(long conversationId, long lastNumber) {
        return em.createNamedQuery("Message.getMessages")
                .setParameter("lastMessageNumber", lastNumber).setParameter("conversationId", conversationId).getResultList();
    }

    @Override
    public long getLastMessageNumber(long conversationId) {
        return em.find(Conversation.class, conversationId).getLastMessageNumber();
    }
}
