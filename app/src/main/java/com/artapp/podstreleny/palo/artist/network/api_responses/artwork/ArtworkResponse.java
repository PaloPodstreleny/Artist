package com.artapp.podstreleny.palo.artist.network.api_responses.artwork;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

public class ArtworkResponse {

    @SerializedName("_links")
    private ImportantLink links;

    @SerializedName("_embedded")
    private ArtworkData data;

    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public ArtworkData getData() {
        return data;
    }

    public void setData(ArtworkData data) {
        this.data = data;
    }
}
