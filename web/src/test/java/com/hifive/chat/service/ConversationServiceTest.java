package com.hifive.chat.service;

import com.hifive.chat.AbstractTest;
import com.hifive.chat.model.Conversation;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
