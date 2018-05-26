package com.itis.pochta.model.request;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PickUpForm {

    @SerializedName("pick_up")
    private List<String> pickUpList;

    @SerializedName("leave")
    private List<String> leaveList;

    public PickUpForm() {
    }

    public PickUpForm(List<String> pickUpList, List<String> leaveList) {
        this.pickUpList = pickUpList;
        this.leaveList = leaveList;
    }

    public List<String> getPickUpList() {
        return pickUpList;
    }

    public void setPickUpList(List<String> pickUpList) {
        this.pickUpList = pickUpList;
    }

    public List<String> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<String> leaveList) {
        this.leaveList = leaveList;
    }
}
