package com.artapp.podstreleny.palo.artist.repositories.artworks;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtistDao;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.db.entity.ArtistsArtworks;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkBoundResource;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.artists.ArtistData;
import com.artapp.podstreleny.palo.artist.network.api_responses.artists.ArtistResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;

import java.util.ArrayList;
import java.util.List;

public class ArtworkDetailRepository {

    private static ArtworkDetailRepository INSTANCE;
    private ArtworkDao mDao;
    private ArtistDao mArtistDao;
    private AppExecutor mAppExecutor;
    private ArtsyEndpoint mEndpoint;

    private ArtworkDetailRepository(ArtworkDao dao, ArtistDao mArtistDao, AppExecutor mAppExecutor, ArtsyEndpoint mEndpoint){
        this.mArtistDao = mArtistDao;
        mDao = dao;
        this.mAppExecutor = mAppExecutor;
        this.mEndpoint = mEndpoint;
    }

    public static ArtworkDetailRepository getInstance(Application context){
        if(INSTANCE == null){
            synchronized (ArtworkDetailRepository.class){
                INSTANCE = new ArtworkDetailRepository(
                        ArtsyDatabase.getDatabaseInstance(context).getArtworkDao(),
                        ArtsyDatabase.getDatabaseInstance(context).getArtistDao(),
                        AppExecutor.getInstance(),
                        RetrofitProvider.getService(ArtsyEndpoint.class)
                );
            }
        }
        return INSTANCE;
    }


    public LiveData<Artwork> getArtwork(String input) {
        return mDao.getArtwork(input);
    }


    public LiveData<Resource<List<Artist>>> getArtistsByArtwork(final String token, final String input){
        return new NetworkBoundResource<List<Artist>,ArtistResponse>(mAppExecutor){
            @Override
            protected void saveCallResult(@NonNull ArtistResponse bodyResponse) {
                final ArtistData data = bodyResponse.getData();
                final ImportantLink links = bodyResponse.getLinks();

                if (data != null && links != null) {
                    final List<Artist> artists = bodyResponse.getData().getArtists();
                    if (artists != null) {
                        final String nextFetch = bodyResponse.getLinks().getNext().getHref();
                        final ArrayList<ArtistsArtworks> list = new ArrayList<>();
                        for (int x = 0; x < artists.size(); x++) {
                            final Artist artwork = artists.get(x);
                            final Link imageURL = artists.get(x).getLinks().getThumbnail();
                            if (imageURL != null) {
                                artwork.setThumbnail(imageURL.getHref());
                            }
                            if(nextFetch != null) {
                                artwork.setNextPage(nextFetch);
                            }
                            list.add(new ArtistsArtworks(input,artwork.getId()));
                        }
                        mArtistDao.insertAll(artists);
                        mArtistDao.insertAllArtistsArtworks(list);

                    }

                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Artist> data) {
                return (data == null || data.size() == 0);
            }

            @NonNull
            @Override
            protected LiveData<List<Artist>> loadFromDb() {
                return mArtistDao.getArtistByArtwork(input);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArtistResponse>> createCall() {
                return mEndpoint.getArtistsByArtwork(token,input);
            }

            @Override
            protected void onFetchFailed() {
                Log.v("ArtworkDetailRepository","OnFetchFailed artworkDetailRepository");
            }
        }.getAsLiveData();
    }

}
