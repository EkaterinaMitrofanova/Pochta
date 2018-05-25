package com.itis.pochta.model.base;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.itis.pochta.model.response.LoginResponseBody;

@Entity
public class User extends LoginResponseBody{

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("second_name")
    private String secondName;

    private String email;
    private String phone;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return phone;
    }

    public void setNumber(String number) {
        this.phone = number;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
