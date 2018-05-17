package com.itis.pochta.model.base;

import com.itis.pochta.model.response.LoginResponseBody;

public class User extends LoginResponseBody{

    private String name;
    private String email;
    private String number;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
