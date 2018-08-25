package com.artapp.podstreleny.palo.artist.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkBoundResource;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;

import java.util.List;

public class ArtworkRepository {

    private static ArtworkRepository INSTANCE;
    private static final String TAG = ArtworkRepository.class.getSimpleName();
    private AppExecutor appExecutor;
    private ArtworkDao mArtworkDao;
    private ArtsyEndpoint mEndpoint;
    private String nextFetch;

    private ArtworkRepository( AppExecutor appExecutor, ArtworkDao dao, ArtsyEndpoint endpoint){
        this.appExecutor = appExecutor;
        this.mArtworkDao = dao;
        this.mEndpoint = endpoint;
    }

    public static ArtworkRepository getInstance(Application application){
        if(INSTANCE == null){
            synchronized (ArtworkRepository.class){
                INSTANCE = new ArtworkRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getArtworkDao(),
                        RetrofitProvider.getService(ArtsyEndpoint.class)
                );
            }
        }
        return INSTANCE;
    }

    public LiveData<Resource<List<Artwork>>> getArtworks(final String token){
        return new NetworkBoundResource<List<Artwork>,ArtworkResponse>(appExecutor){

            @Override
            protected void saveCallResult(@NonNull ArtworkResponse item) {
                final List<Artwork> artworks = item.getData().getArtworks();
                nextFetch = item.getLinks().getNext().getHref();
                for(int x = 0; x < artworks.size(); x++){
                    final Artwork artwork = artworks.get(x);
                    artwork.setThumbnail(item.getData().getArtworks().get(x).getLinks().getThumbnail().getHref());
                }

                mArtworkDao.insertAll(artworks);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Artwork> data) {
                return (data == null || data.size() == 0);
            }

            @NonNull
            @Override
            protected LiveData<List<Artwork>> loadFromDb() {
                return mArtworkDao.getArtworks();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArtworkResponse>> createCall() {
                return mEndpoint.getArtworks(token);
            }

            @Override
            protected void onFetchFailed() {
                Log.e(TAG,"Problem with fetching data - ArtworkRepository class");
            }
        }.getAsLiveData();
    }




}
