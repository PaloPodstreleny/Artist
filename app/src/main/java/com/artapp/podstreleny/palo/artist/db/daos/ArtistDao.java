package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.db.entity.ArtistsArtworks;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM artists")
    DataSource.Factory<Integer,Artist> getArtists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Artist> data);

    //Wrong SQL change later
    @Query("SELECT a.* FROM artists a, artists_artworks b WHERE a.id = b.artists and b.artworks = :input")
    LiveData<List<Artist>> getArtistByArtwork(String input);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllArtistsArtworks(List<ArtistsArtworks> data);


}
