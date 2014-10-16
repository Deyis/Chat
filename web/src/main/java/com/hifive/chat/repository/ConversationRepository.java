package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.repository.BaseRepository;
import com.hifive.security.model.User;

import java.util.List;

public interface ConversationRepository extends BaseRepository<Conversation> {


    Conversation create(User firstUser, User secondUser, String language);
    Conversation findFreeConversationAndJoin(String lang, User user);
    long getLastMessageNumber(long conversationId);

}
