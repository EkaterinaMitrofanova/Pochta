package com.itis.pochta.model.request;

import com.google.gson.annotations.SerializedName;

public class LoginForm {

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginForm() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
