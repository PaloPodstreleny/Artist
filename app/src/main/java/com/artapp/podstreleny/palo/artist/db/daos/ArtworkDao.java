package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;

import java.util.List;

@Dao
public interface ArtworkDao {

    @Query("SELECT * FROM artworks")
    DataSource.Factory<Integer,Artwork> getArtworks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Artwork> data);

    @Query("SELECT * FROM artworks LIMIT 1 OFFSET :number")
    Artwork getRandomArtwork(int number);

    @Query("SELECT COUNT(*) AS n FROM artworks")
    int getNumberOfRows();

    @Query("SELECT artworks.next_page FROM artworks LIMIT 1")
    String getNextPage();

    @Query("SELECT * FROM artworks LIMIT 10")
    List<Artwork> getArtworksForWidget();


    @Query("SELECT * FROM artworks WHERE artworks.id = :id")
    LiveData<Artwork> getArtwork(String id);
}
