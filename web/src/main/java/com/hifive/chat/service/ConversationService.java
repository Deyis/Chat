package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.common.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.common.service.BaseService;
import com.hifive.security.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ConversationService extends BaseService {

    Conversation createConversationFromRequest(CreateConversationRequest request, User currentUser);
    long addMessage(SendMessageRequest request, User currentUser);
    Pair<List<Message>, Long> getMessages(long conversationId, long lastNumber);
    Conversation checkConversation(long conversationId);
}
