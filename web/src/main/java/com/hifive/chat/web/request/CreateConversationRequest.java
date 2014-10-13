package com.hifive.chat.web.request;

public class CreateConversationRequest implements BaseRequest {

    public CreateConversationRequest() {
    }

    private Long userId;

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
