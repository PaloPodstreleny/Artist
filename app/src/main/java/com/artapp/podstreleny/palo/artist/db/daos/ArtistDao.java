package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Artist;

import java.util.List;

@Dao
public interface ArtistDao {

    @Query("SELECT * FROM artists")
    DataSource.Factory<Integer,Artist> getArtists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Artist> data);

}
