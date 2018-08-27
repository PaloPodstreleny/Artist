package com.artapp.podstreleny.palo.artist.network.api_responses.artists;

import com.artapp.podstreleny.palo.artist.db.entity.Artist;

import java.util.List;

public class ArtistData {
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
