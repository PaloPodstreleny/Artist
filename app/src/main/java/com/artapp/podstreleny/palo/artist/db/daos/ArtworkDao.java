package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Update;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.db.entity.ArtworkNexPage;

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

    //TODO check if it's correct
    @Query("SELECT artworks.next_page FROM artworks LIMIT 1")
    String getNextPage();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void updateNextPage(ArtworkNexPage page);

}
