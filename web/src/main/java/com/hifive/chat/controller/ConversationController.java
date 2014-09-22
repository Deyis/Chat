package com.hifive.chat.controller;

import com.hifive.chat.model.Conversation;
import com.hifive.chat.service.ConversationService;
import com.hifive.chat.web.request.CreateConversationRequest;
import com.hifive.chat.web.response.BaseResponse;
import com.hifive.chat.web.response.CreateConversationResponse;
import com.hifive.chat.web.response.WaitForConversationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody public BaseResponse create(@RequestBody CreateConversationRequest request) {
        Conversation conversation = conversationService.createConversationFromRequest(request);
        if (conversation == null) {
            return new WaitForConversationResponse();
        }
        return new CreateConversationResponse(conversation.getId());
    }

}
