package com.optionringringtone.newringtonefree.object;

import com.google.gson.annotations.SerializedName;

public class RequestRingtoneResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
