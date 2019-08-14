package com.optionringringtone.newringtonefree.object;

import com.google.gson.annotations.SerializedName;

public class ResponseDTO<Object> {

    @SerializedName("total")
    private int total;
    @SerializedName("to")
    private int to;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("path")
    private String path;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("last_page_url")
    private String lastPageUrl;
    @SerializedName("last_page")
    private int lastPage;
    @SerializedName("from")
    private int from;
    @SerializedName("first_page_url")
    private String firstPageUrl;
    @SerializedName("data")
    private Object data;
    @SerializedName("current_page")
    private int currentPage;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}


