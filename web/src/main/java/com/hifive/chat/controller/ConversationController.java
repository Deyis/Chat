package com.hifive.chat.controller;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.service.ConversationService;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.response.BaseResponse;
import com.hifive.chat.web.response.CreateConversationResponse;
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

}
