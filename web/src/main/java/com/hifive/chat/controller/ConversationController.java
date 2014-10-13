package com.hifive.chat.controller;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.model.Message;
import com.hifive.chat.service.ConversationService;
import com.hifive.common.util.Pair;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.request.SendMessageRequest;
import com.hifive.common.web.response.BaseResponse;
import com.hifive.chat.web.response.CreateConversationResponse;
import com.hifive.chat.web.response.MessagesResponse;
import com.hifive.chat.web.response.WaitForConversationResponse;
import com.hifive.security.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestBody CreateConversationRequest request) {
        System.out.println(request.getLang());
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Conversation conversation = conversationService.createConversationFromRequest(request, currentUser);
        if (conversation.getSecondUser() == null) {
            return new WaitForConversationResponse(conversation.getId());
        }
        return new CreateConversationResponse(conversation.getId());
    }

    @RequestMapping(value = "/check/{conversationId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse check(@PathVariable("conversationId") long conversationId) {
        Conversation conversation = conversationService.checkConversation(conversationId);
        if (conversation.getSecondUser() == null) {
            return new WaitForConversationResponse(conversation.getId());
        }
        return new CreateConversationResponse(conversation.getId());
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse sendMessage(@RequestBody SendMessageRequest request) {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        conversationService.addMessage(request, currentUser);
        Pair<List<Message>, Long> result = conversationService.getMessages(request.getConversationId(), request.getLastNumber());

        MessagesResponse response = new MessagesResponse();
        response.setLastNumber(result.getSecond());
        response.setNewMessages(result.getFirst());
        return response;
    }

    @RequestMapping(value = "/{conversationId}/leave", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse leave(@PathVariable("conversationId") long conversationId) {
        return null;
    }

    @RequestMapping(value = "/{conversationId}/messages/{lastMessageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getMessage(@PathVariable("conversationId") long conversationId,
                                   @PathVariable("lastMessageNumber") long lastMessageNumber) {


        Pair<List<Message>, Long> result = conversationService.getMessages(conversationId, lastMessageNumber);

        MessagesResponse response = new MessagesResponse();
        response.setLastNumber(result.getSecond());
        response.setNewMessages(result.getFirst());
        return response;
    }

}
