package com.artapp.podstreleny.palo.artist.db.daos;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.artapp.podstreleny.palo.artist.db.entity.Gene;

import java.util.List;

@Dao
public interface GeneDao {

    @Query("SELECT * FROM genes")
    DataSource.Factory<Integer,Gene> getGenes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Gene> data);


}
