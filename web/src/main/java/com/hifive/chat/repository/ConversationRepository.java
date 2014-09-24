package com.hifive.chat.repository;

import com.hifive.chat.model.Conversation;
import com.hifive.security.model.User;

public interface ConversationRepository extends BaseRepository<Conversation> {

    public Conversation findById(Long id);
    public Conversation create(User firstUser, User secondUser, String language);
    public Conversation findFreeConversationAndJoin(String lang, User user);
}
