package com.artapp.podstreleny.palo.artist.network.api_responses.shows;

import com.artapp.podstreleny.palo.artist.db.entity.Show;

import java.util.List;

public class ShowData {
    private List<Show> shows;

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}