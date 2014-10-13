package com.hifive.chat.service;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.chat.repository.ConversationRepository;
import com.hifive.common.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
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
        if (conversation == null || !(isUserInConversation(conversation, currentUser))) {
            return 0;
        }
        conversationRepository.addMessageToConversation(conversation, request.getMessage(), currentUser);
        return conversationRepository.getLastMessageNumber(request.getConversationId());
    }

    @Override
    public Pair<List<Message>, Long> getMessages(long conversationId, long lastNumber) {
        List<Message> newMessages = conversationRepository.getMessagesForConversation(conversationId, lastNumber);
        Long newLastNumber = conversationRepository.getLastMessageNumber(conversationId);
        return  new Pair<>(newMessages, newLastNumber);
    }


    @Override
    public Conversation checkConversation(long conversationId) {
        return conversationRepository.findById(conversationId);
    }

    private boolean isTheSameUser(User a, User b) {
        return Comparator.comparing(User::getId).compare(a, b) == 0;
    }

    private boolean isAnyMatch(User a, User... others) {
        return Arrays.asList(others).stream().anyMatch((b) -> isTheSameUser(a, b));
    }

    private boolean isUserInConversation(Conversation conversation, User user) {
        return isAnyMatch(user, conversation.getFirstUser(), conversation.getSecondUser());
    }
}
