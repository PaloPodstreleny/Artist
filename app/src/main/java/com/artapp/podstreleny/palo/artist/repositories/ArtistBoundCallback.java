package com.artapp.podstreleny.palo.artist.repositories;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.daos.ArtistDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.NetworkResource;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.artists.ArtistData;
import com.artapp.podstreleny.palo.artist.network.api_responses.artists.ArtistResponse;

import java.util.List;

import retrofit2.Call;

public class ArtistBoundCallback extends PagedList.BoundaryCallback<Artist> {
    private static final String TAG = ArtistBoundCallback.class.getSimpleName();
    private static final int PREFETCH_SIZE = 50;
    private AppExecutor executor;
    private NetworkCallback callback;
    private ArtsyEndpoint endpoint;
    private String token;
    private ArtistDao dao;

    private boolean isLoaded;

    public ArtistBoundCallback(String token, ArtistDao dao, AppExecutor executor, ArtsyEndpoint endpoint,NetworkCallback callback) {
        this.executor = executor;
        this.endpoint = endpoint;
        this.token = token;
        this.dao = dao;
        this.callback = callback;
    }

    @Override
    public void onZeroItemsLoaded() {
        if(isLoaded) return;
        isLoaded = true;
        new NetworkResource<ArtistResponse>(executor,callback,true){
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"ZeroItemsLoaded problem!");
            }

            @NonNull
            @Override
            protected Call<ArtistResponse> createCall() {
                return endpoint.getArtists(token,PREFETCH_SIZE,true);
            }

            @Override
            protected void saveCallResult(@NonNull ArtistResponse item) {
                isLoaded = false;
                saveArtistsData(item);
            }
        };
    }

    @Override
    public void onItemAtEndLoaded(@NonNull final Artist itemAtEnd) {
        if(isLoaded) return;
        isLoaded = true;
        new NetworkResource<ArtistResponse>(executor,callback,false){
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"OnItemAtEndLoaded problem!");
            }

            @NonNull
            @Override
            protected Call<ArtistResponse> createCall() {
                return endpoint.getNextArtistPage(itemAtEnd.getNextPage(),token);
            }

            @Override
            protected void saveCallResult(@NonNull ArtistResponse item) {
                isLoaded = false;
                saveArtistsData(item);
            }
        };
    }


    @WorkerThread
    private void saveArtistsData(ArtistResponse bodyResponse) {
        final ArtistData data = bodyResponse.getData();
        final ImportantLink links = bodyResponse.getLinks();

        if (data != null && links != null) {
            final List<Artist> artists = bodyResponse.getData().getArtists();
            if (artists != null) {
                final String nextFetch = bodyResponse.getLinks().getNext().getHref();
                for (int x = 0; x < artists.size(); x++) {
                    final Artist artwork = artists.get(x);
                    final Link imageURL = artists.get(x).getLinks().getThumbnail();
                    if (imageURL != null) {
                        artwork.setThumbnail(imageURL.getHref());
                    }
                    if(nextFetch != null) {
                        artwork.setNextPage(nextFetch);
                    }
                }
                dao.insertAll(artists);
            }

        }
    }


}