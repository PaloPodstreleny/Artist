package com.artapp.podstreleny.palo.artist.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "artists_artworks")
public class ArtistsArtworks {

    @PrimaryKey
    private int id;
    private String artworks;
    private String artists;

    public ArtistsArtworks(){

    }

    @Ignore
    public ArtistsArtworks(String artworks, String artists){
        this.artworks = artworks;
        this.artists = artists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtworks() {
        return artworks;
    }

    public void setArtworks(String artworks) {
        this.artworks = artworks;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
}
