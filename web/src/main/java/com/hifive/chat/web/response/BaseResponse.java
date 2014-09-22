package com.hifive.chat.web.response;


public interface BaseResponse {

    int getCode();

    public enum ResponseCode {
        CREATE_CONVERSATION_RESPONSE(1),
        WAIT_FOR_CONVERSATION_RESPONSE(2);

        private int code;

        ResponseCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
