package com.hifive.chat.repository;


import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.repository.AbstractRepository;
import com.hifive.security.model.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class MessageRepositoryImpl extends AbstractRepository<Message> implements MessageRepository {


    @Override
    public List<Message> getMessagesForConversation(long conversationId, long lastNumber) {
        return getListByNamedQuery("Message.getMessages", "lastMessageNumber", lastNumber, "conversationId", conversationId);
    }

    @Override
    public void addMessageToConversation(Conversation conversation, String message, User user) {
        Long lastNumber = conversation.getLastMessageNumber() + 1;
        conversation.setLastMessageNumber(lastNumber);
        Message newMessage = new Message();
        newMessage.setUser(user);
        newMessage.setMessage(message);
        newMessage.setConversation(conversation);
        newMessage.setMessageNumber(lastNumber);
        newMessage.setCreationDate(new Date());
        merge(newMessage);
    }

    @Override
    public Class<Message> getEntityClass() {
        return Message.class;
    }
}
