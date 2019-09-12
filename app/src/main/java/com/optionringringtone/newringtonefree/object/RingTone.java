package com.optionringringtone.newringtonefree.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RingTone implements Serializable {
    @SerializedName("url")
    private String url;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("filepath")
    private String filepath;
    @SerializedName("duration")
    private String duration;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    private boolean isDownLoad;
    private String group;
    private boolean isPlaying;
    private boolean isChangeStatus;
    private String path;
    private long timeDownload;
    public RingTone() {
    }

    public RingTone(String url, String updatedAt, String createdAt, String filepath, String duration, String name, int id, boolean isDownLoad, boolean isPlaying) {
        this.url = url;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.filepath = filepath;
        this.duration = duration;
        this.name = name;
        this.id = id;
        this.isDownLoad = isDownLoad;
        this.isPlaying = isPlaying;
    }

    public RingTone(String url, String updatedAt, String createdAt, String filepath, String duration, String name, int id, boolean isDownLoad, String group, boolean isPlaying) {
        this.url = url;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.filepath = filepath;
        this.duration = duration;
        this.name = name;
        this.id = id;
        this.isDownLoad = isDownLoad;
        this.group = group;
        this.isPlaying = isPlaying;
    }

    public RingTone(String url,String name, String duration){
        this.url=url;
        this.name=name;
        this.duration=duration;
    }
    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isChangeStatus() {
        return isChangeStatus;
    }

    public void setChangeStatus(boolean changeStatus) {
        isChangeStatus = changeStatus;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimeDownload() {
        return timeDownload;
    }

    public void setTimeDownload(long timeDownload) {
        this.timeDownload = timeDownload;
    }
}