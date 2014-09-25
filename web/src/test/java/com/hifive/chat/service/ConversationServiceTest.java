package com.hifive.chat.service;

import com.hifive.chat.AbstractTest;
import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.chat.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConversationServiceTest extends AbstractTest {

    @Autowired
    ConversationService conversationService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createNewConversation() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");

        CreateConversationRequest request = new CreateConversationRequest();
        request.setLang("en");
        Conversation conversation = conversationService.createConversationFromRequest(request, firstUser);
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());

        conversation = conversationService.checkConversation(conversation.getId());
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());
    }


    @Test
    public void joinToConversation() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        CreateConversationRequest request = new CreateConversationRequest();
        request.setLang("en");
        Conversation conversation = conversationService.createConversationFromRequest(request, firstUser);
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());

        conversation = conversationService.checkConversation(conversation.getId());
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());

        Conversation secondUserConversation = conversationService.createConversationFromRequest(request, secondUser);
        Assert.assertNotNull(secondUserConversation);
        Assert.assertEquals(secondUserConversation.getSecondUser().getId(), secondUser.getId());
    }

    @Test
    public void sendMessage() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        CreateConversationRequest request = new CreateConversationRequest();
        request.setLang("en");
        Conversation conversation = conversationService.createConversationFromRequest(request, firstUser);
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());

        conversation = conversationService.checkConversation(conversation.getId());
        Assert.assertNotNull(conversation);
        Assert.assertNull(conversation.getSecondUser());

        Conversation secondUserConversation = conversationService.createConversationFromRequest(request, secondUser);
        Assert.assertNotNull(secondUserConversation);
        Assert.assertEquals(secondUserConversation.getSecondUser().getId(), secondUser.getId());

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setConversationId(conversation.getId());
        sendMessageRequest.setMessage("test");
        long lastNumber = conversationService.addMessage(sendMessageRequest, firstUser);
        Pair<List<Message>, Long> result = conversationService.getMessages(conversation.getId(), 0);
        Assert.assertEquals(Long.valueOf(lastNumber), result.getSecond());
        Assert.assertEquals(result.getFirst().get((int)lastNumber -1).getMessage(), sendMessageRequest.getMessage());
    }
}
