package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.chat.repository.ConversationRepository;
import com.hifive.chat.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public long addMessage(SendMessageRequest request, User currentUser) {
        Conversation conversation = conversationRepository.findById(request.getConversationId());
        if (conversation == null ||
                !(
                    conversation.getFirstUser().getId().equals(currentUser.getId()) ||
                    conversation.getSecondUser().getId().equals(currentUser.getId())
                )) {
            return 0;
        }
        conversationRepository.addMessageToConversation(conversation, request.getMessage(), currentUser);
        return conversationRepository.getLastMessageNumber(request.getConversationId());
    }

    @Override
    public Pair<List<Message>, Long> getMessages(long conversationId, long lastNumber) {
        List<Message> newMessages = conversationRepository.getMessagesForConversation(conversationId, lastNumber);
        Long newLastNumber = conversationRepository.getLastMessageNumber(conversationId);
        return  new Pair<List<Message>, Long>(newMessages, newLastNumber);
    }


    @Override
    public Conversation checkConversation(long conversationId) {
        return conversationRepository.findById(conversationId);
    }
}
