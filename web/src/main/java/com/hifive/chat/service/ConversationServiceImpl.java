package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Queue;
import com.hifive.chat.repository.ConversationRepository;
import com.hifive.chat.repository.QueueRepository;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Override
    public Conversation createConversationFromRequest(CreateConversationRequest request) {
        Queue queue = queueRepository.pull(request.getLang());
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (queue == null) {
            queueRepository.push(currentUser, request.getLang());
            return null;
        }
        return conversationRepository.create(currentUser, queue.getUser(), request.getLang());
    }
}
