package com.optionringringtone.newringtonefree.object.detailcategory;

import android.media.Ringtone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {
    @SerializedName("ringtones")
    @Expose
    private List<Ringtone> ringtones = null;

    public List<Ringtone> getRingtones() {
        return ringtones;
    }

    public void setRingtones(List<Ringtone> ringtones) {
        this.ringtones = ringtones;
    }
}
