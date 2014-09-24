package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.repository.ConversationRepository;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Conversation createConversationFromRequest(CreateConversationRequest request, User currentUser) {
        Conversation conversation = conversationRepository.findFreeConversationAndJoin(request.getLang(), currentUser);
        if (conversation != null) {
            return conversation;
        }
        return conversationRepository.create(currentUser, null, request.getLang());
    }

    @Override
    public Conversation checkConversation(long conversationId) {
        return conversationRepository.findById(conversationId);
    }
}
