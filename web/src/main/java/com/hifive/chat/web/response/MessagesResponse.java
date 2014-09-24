package com.hifive.chat.web.response;


import com.hifive.chat.model.Message;

import java.util.List;

public class MessagesResponse extends AbstractResponse implements BaseResponse {

    private long lastNumber;
    private List<Message> newMessages;

    public MessagesResponse() {
        super(ResponseCode.MESSAGES_RESPONSE.getCode());
    }

    public MessagesResponse(long lastNumber) {
        super(ResponseCode.MESSAGES_RESPONSE.getCode());
        this.lastNumber = lastNumber;
    }

    public long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(long lastNumber) {
        this.lastNumber = lastNumber;
    }

    public List<Message> getNewMessages() {
        return newMessages;
    }

    public void setNewMessages(List<Message> newMessages) {
        this.newMessages = newMessages;
    }
}
