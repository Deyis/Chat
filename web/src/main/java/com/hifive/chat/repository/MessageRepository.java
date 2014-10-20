package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.repository.BaseRepository;
import com.hifive.security.model.User;

import java.util.List;


public interface MessageRepository extends BaseRepository<Message> {

    List<Message> getMessagesForConversation(long conversationId, long lastNumber);

    void addMessageToConversation(Conversation conversation, String message, User user);
}
