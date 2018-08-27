package com.artapp.podstreleny.palo.artist.network.api_responses.genes;

import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.google.gson.annotations.SerializedName;

public class GeneResponse {

    @SerializedName("_links")
    private ImportantLink links;

    @SerializedName("_embedded")
    private GeneData data;


    public ImportantLink getLinks() {
        return links;
    }

    public void setLinks(ImportantLink links) {
        this.links = links;
    }

    public GeneData getData() {
        return data;
    }

    public void setData(GeneData data) {
        this.data = data;
    }
}
