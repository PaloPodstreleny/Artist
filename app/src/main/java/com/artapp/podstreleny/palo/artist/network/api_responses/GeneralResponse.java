package com.artapp.podstreleny.palo.artist.network.api_responses;

import com.google.gson.annotations.SerializedName;


public abstract class GeneralResponse<T> {

    @SerializedName("_embedded")
    private T data;

    @SerializedName("_links")
    private ImportantLink links;

    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
