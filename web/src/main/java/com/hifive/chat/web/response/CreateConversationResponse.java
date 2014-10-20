package com.hifive.chat.web.response;

import com.hifive.common.web.response.AbstractResponse;

public class CreateConversationResponse extends AbstractResponse {

    private Long conversationId;

    public CreateConversationResponse() {
        super(ResponseCode.CREATE_CONVERSATION_RESPONSE.getCode());
    }

    public CreateConversationResponse(Long conversationId) {
        super(ResponseCode.CREATE_CONVERSATION_RESPONSE.getCode());
        this.conversationId = conversationId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
