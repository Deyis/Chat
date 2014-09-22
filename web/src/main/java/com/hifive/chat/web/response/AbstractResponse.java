package com.hifive.chat.web.response;

public abstract class AbstractResponse implements BaseResponse {

    protected int code;

    public AbstractResponse(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
