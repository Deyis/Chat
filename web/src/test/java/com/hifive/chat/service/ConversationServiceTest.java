package com.hifive.chat.service;

import com.hifive.chat.AbstractTest;
import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.chat.repository.ConversationRepository;
import com.hifive.common.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.security.model.User;
import com.hifive.security.service.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class ConversationServiceTest extends AbstractTest {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;


    @Test
    public void createNewConversation() {
        User user = (User) userRepository.loadUserByUsername("admin");
        Conversation conversation = createConversationForUser(user, "en");

        Assert.assertNull(conversation.getSecondUser());

        conversationRepository.remove(conversation);
    }

    @Test
    public void checkNewConversation() {
        User user = (User) userRepository.loadUserByUsername("admin");
        Conversation conversation = createConversationForUser(user, "en");

        conversation = conversationService.checkConversation(conversation.getId());

        Assert.assertNull(conversation.getSecondUser());

        conversationRepository.remove(conversation);
    }

    @Test
    public void joinToConversation() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        Conversation conversation = createConversationForTwoPeople(firstUser, secondUser, "en");

        Assert.assertEquals(conversation.getFirstUser().getId(), firstUser.getId());
        Assert.assertEquals(conversation.getSecondUser().getId(), secondUser.getId());

        conversationRepository.remove(conversation);
    }

    @Test
    public void sendMessage() {
        User firstUser = (User) userRepository.loadUserByUsername("admin");
        User secondUser = (User) userRepository.loadUserByUsername("user");

        Conversation conversation = createConversationForTwoPeople(firstUser, secondUser, "en");

        SendMessageRequest sendMessageRequest = createTestSendMessageRequest(conversation.getId());

        long initialLastNumber = 0;
        long lastNumber = conversationService.addMessage(sendMessageRequest, firstUser);
        Pair<List<Message>, Long> result = conversationService.getMessages(conversation.getId(), initialLastNumber);

        Assert.assertEquals(Long.valueOf(lastNumber), result.getSecond());
        Assert.assertEquals(result.getFirst().get((int)lastNumber -1).getMessage(), sendMessageRequest.getMessage());

        conversationRepository.remove(conversation);
    }


    private Conversation createConversationForUser(User user, String lang) {
        CreateConversationRequest request = new CreateConversationRequest();
        request.setLang(lang);
        Conversation conversation = conversationService.createConversationFromRequest(request, user);

        Assert.assertNotNull(conversation);
        return conversation;
    }

    private Conversation createConversationForTwoPeople(User first, User second, String lang) {
        Long createdConversationId = createConversationForUser(first, lang).getId();
        Conversation conversation = createConversationForUser(second, lang);

        Assert.assertEquals(createdConversationId, conversation.getId());
        return conversation;
    }

    private SendMessageRequest createTestSendMessageRequest(Long conversationId) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setConversationId(conversationId);
        sendMessageRequest.setMessage("test");
        return sendMessageRequest;
    }

}
