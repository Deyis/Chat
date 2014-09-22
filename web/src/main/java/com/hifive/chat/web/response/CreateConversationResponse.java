package com.hifive.chat.web.response;

public class CreateConversationResponse extends AbstractResponse {

    private Long conversationId;

    public CreateConversationResponse() {
        super(ResponseCode.CREATE_CONVERSATION_RESPONSE.getCode());
    }

    public CreateConversationResponse(Long conversationId) {
        super(ResponseCode.CREATE_CONVERSATION_RESPONSE.getCode());
        this.conversationId = conversationId;
    }

    public long getConversationId() {
        return conversationId;
    }

    public void setConversationId(long conversationId) {
        this.conversationId = conversationId;
    }
}
