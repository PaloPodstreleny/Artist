package com.artapp.podstreleny.palo.artist.network.api_responses.shows;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

public class ShowResponse {

    @SerializedName("_links")
    private ImportantLink links;

    @SerializedName("_embedded")
    private ShowData data;


    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public ShowData getData() {
        return data;
    }

    public void setData(ShowData data) {
        this.data = data;
    }
}