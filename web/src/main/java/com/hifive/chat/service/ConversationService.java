package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.security.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConversationService extends BaseService {

    Conversation createConversationFromRequest(CreateConversationRequest request, User currentUser);
    public Conversation checkConversation(long conversationId);
}
