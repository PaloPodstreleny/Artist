package com.artapp.podstreleny.palo.artist.repositories.artworks;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;

public class ArtworkDetailRepository {

    private static ArtworkDetailRepository INSTANCE;
    private ArtworkDao mDao;

    private ArtworkDetailRepository(ArtworkDao dao){
        mDao = dao;
    }

    public static ArtworkDetailRepository getInstance(Application context){
        if(INSTANCE == null){
            synchronized (ArtworkDetailRepository.class){
                INSTANCE = new ArtworkDetailRepository(
                        ArtsyDatabase.getDatabaseInstance(context).getArtworkDao()
                );
            }
        }
        return INSTANCE;
    }


    public LiveData<Artwork> getArtwork(String input) {
        return mDao.getArtwork(input);
    }
}
