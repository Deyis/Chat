package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.web.request.CreateConversationRequest;

public interface ConversationService extends BaseService {

    Conversation createConversationFromRequest(CreateConversationRequest request);
}
