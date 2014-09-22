package com.hifive.chat.web.request;

public class CreateConversationRequest implements BaseRequest {

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
