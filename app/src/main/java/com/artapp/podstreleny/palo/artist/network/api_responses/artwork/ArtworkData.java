package com.artapp.podstreleny.palo.artist.network.api_responses.artwork;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import java.util.List;

public class ArtworkData {

    private List<Artwork> artworks;

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}
