package com.itis.pochta.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponseBody {

    @SerializedName("user_id")
    private long id;

    @SerializedName("role")
    private String role;

    @SerializedName("token")
    private String token;

    public LoginResponseBody(long id, String role, String token) {
        this.id = id;
        this.role = role;
        this.token = token;
    }

    public LoginResponseBody() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}