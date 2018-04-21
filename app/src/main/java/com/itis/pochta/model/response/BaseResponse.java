package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("body")
    private T body;

    public BaseResponse(int code, T body) {
        this.code = code;
        this.body = body;
    }

    public BaseResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
