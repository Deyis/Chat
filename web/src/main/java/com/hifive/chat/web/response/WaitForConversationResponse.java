package com.hifive.chat.web.response;


import com.hifive.common.web.response.AbstractResponse;

public class WaitForConversationResponse extends AbstractResponse {

    private Long conversationId;

    public WaitForConversationResponse() {
        super(ResponseCode.WAIT_FOR_CONVERSATION_RESPONSE.getCode());
    }

    public WaitForConversationResponse(Long conversationId) {
        super(ResponseCode.WAIT_FOR_CONVERSATION_RESPONSE.getCode());
        this.conversationId = conversationId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
