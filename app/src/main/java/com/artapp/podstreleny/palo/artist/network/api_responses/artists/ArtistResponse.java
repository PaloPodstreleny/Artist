package com.artapp.podstreleny.palo.artist.network.api_responses.artists;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

public class ArtistResponse {

    @SerializedName("_links")
    private ImportantLink links;

    @SerializedName("_embedded")
    private ArtistData data;


    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public ArtistData getData() {
        return data;
    }

    public void setData(ArtistData data) {
        this.data = data;
    }
}

