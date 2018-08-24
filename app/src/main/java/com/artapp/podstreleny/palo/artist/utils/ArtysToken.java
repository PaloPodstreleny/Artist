package com.artapp.podstreleny.palo.artist.utils;

import com.google.gson.annotations.SerializedName;

public class ArtysToken {

    public String type;
    private String token;
    @SerializedName("expires_at")
    private String expireDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
