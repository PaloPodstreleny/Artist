package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Show;

import java.util.List;

@Dao
public interface ShowDao {

    @Query("SELECT * FROM shows")
    DataSource.Factory<Integer,Show> getShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Show> data);


}
