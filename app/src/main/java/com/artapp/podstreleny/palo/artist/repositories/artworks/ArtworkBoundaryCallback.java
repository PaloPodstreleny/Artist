package com.artapp.podstreleny.palo.artist.repositories.artworks;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.NetworkResource;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkData;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkResponse;
import java.util.List;

import retrofit2.Call;
public class ArtworkBoundaryCallback extends PagedList.BoundaryCallback<Artwork> {

    private static final String TAG = ArtworkBoundaryCallback.class.getSimpleName();
    private static final int PREFETCH_SIZE = 50;
    private AppExecutor executor;
    private NetworkCallback callback;
    private ArtsyEndpoint endpoint;
    private String token;
    private ArtworkDao dao;

    private boolean isLoaded;

    public ArtworkBoundaryCallback(String token, ArtworkDao dao, AppExecutor executor, ArtsyEndpoint endpoint,NetworkCallback callback) {
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
        new NetworkResource<ArtworkResponse>(executor,callback,true){

            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"ZeroItemsLoaded problem!");
            }

            @NonNull
            @Override
            protected Call<ArtworkResponse> createCall() {
                return endpoint.getArtworks(token,PREFETCH_SIZE);
            }

            @Override
            protected void saveCallResult(@NonNull ArtworkResponse bodyResponse) {
                isLoaded = false;
               saveArtworkData(bodyResponse);
            }

        };

    }


    @Override
    public void onItemAtEndLoaded(@NonNull final Artwork itemAtEnd) {
        new NetworkResource<ArtworkResponse>(executor,callback,false){
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"OnItemAtEndLoaded network problem!");
            }

            @NonNull
            @Override
            protected Call<ArtworkResponse> createCall() {
                return endpoint.getNextArtworksPage(itemAtEnd.getNextPage(),token);
            }

            @Override
            protected void saveCallResult(@NonNull ArtworkResponse item) {
                isLoaded = false;
                saveArtworkData(item);
            }
        };
    }

    @WorkerThread
    private void saveArtworkData(@NonNull ArtworkResponse bodyResponse) {
        final ArtworkData data = bodyResponse.getData();
        final ImportantLink links = bodyResponse.getLinks();

        if (data != null && links != null) {
            final List<Artwork> artworks = bodyResponse.getData().getArtworks();
            if (artworks != null) {
                final String nextFetch = bodyResponse.getLinks().getNext().getHref();
                for (int x = 0; x < artworks.size(); x++) {
                    final Artwork artwork = artworks.get(x);
                    final Link imageURL = artworks.get(x).getLinks().getThumbnail();
                    if (imageURL != null) {
                        artwork.setThumbnail(imageURL.getHref());
                    }
                    if(nextFetch != null) {
                        artwork.setNextPage(nextFetch);
                    }
                }
                dao.insertAll(artworks);
            }

        }

    }

}
