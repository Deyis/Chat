package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.repository.BaseRepository;
import com.hifive.security.model.User;

import java.util.List;

public interface ConversationRepository extends BaseRepository<Conversation> {

    Conversation findById(Long id);
    Conversation create(User firstUser, User secondUser, String language);
    Conversation findFreeConversationAndJoin(String lang, User user);

    void addMessageToConversation(Conversation conversation, String message, User user);
    List<Message> getMessagesForConversation(long conversationId, long lastNumber);
    long getLastMessageNumber(long conversationId);

}
