package com.artapp.podstreleny.palo.artist.db;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;

@Database(entities = {Artwork.class}, version = 1, exportSchema = false)
public abstract class ArtsyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "artsy_database";
    private static ArtsyDatabase INSTANCE;

    public abstract ArtworkDao getArtworkDao();

    public static ArtsyDatabase getDatabaseInstance(Application context) {
        if (INSTANCE == null) {
            synchronized (ArtsyDatabase.class) {
                INSTANCE = Room.databaseBuilder(context, ArtsyDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }

}

