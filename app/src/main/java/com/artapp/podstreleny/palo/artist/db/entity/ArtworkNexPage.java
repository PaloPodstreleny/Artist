package com.artapp.podstreleny.palo.artist.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "artwork_next_page")
public class ArtworkNexPage {

    @PrimaryKey
    int id;

    String nextPage;

    public ArtworkNexPage(int id, String nextPage){
        this.id = id;
        this.nextPage = nextPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
