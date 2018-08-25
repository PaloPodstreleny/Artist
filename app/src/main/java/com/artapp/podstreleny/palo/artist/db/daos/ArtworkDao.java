package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;

import java.util.List;

@Dao
public interface ArtworkDao {

    @Query("SELECT * FROM artworks")
    LiveData<List<Artwork>> getArtworks();

}
