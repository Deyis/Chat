package com.hifive.chat.web.response;

public class UserDetailsResponse {

    private String username;

    public UserDetailsResponse() {
    }

    public UserDetailsResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
