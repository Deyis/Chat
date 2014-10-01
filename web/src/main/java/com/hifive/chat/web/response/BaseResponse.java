package com.hifive.chat.web.response;


public interface BaseResponse {

    int getCode();

    public enum ResponseCode {
        CREATE_CONVERSATION_RESPONSE(1),
        WAIT_FOR_CONVERSATION_RESPONSE(2),
        MESSAGE_ADDED(3),
        MESSAGES_RESPONSE(4),
        REGISTRATION_RESPONSE(5);

        private int code;

        ResponseCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
