package com.artapp.podstreleny.palo.artist.network.api_responses;

import java.util.List;

public class GeneralData<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setGenes(List<T> data) {
        this.data = data;
    }
}
