package com.artapp.podstreleny.palo.artist.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.NetworkResourceWorkerThread;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkData;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkResponse;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;

import java.util.List;

import retrofit2.Call;

public class ArtworkIntent extends IntentService {

    private static final String TAG = ArtworkIntent.class.getSimpleName();
    public static final String ARTWORK_INTENT_SERVICE = "artwork_intent_service";
    private static final int LOADING_ARTWORK_SIZE = 50;

    public ArtworkIntent(){
        super(ARTWORK_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        if(intent != null) {

            final AppExecutor executor = AppExecutor.getInstance();
            final ArtsyEndpoint endpoint = RetrofitProvider.getService(ArtsyEndpoint.class);
            final ArtworkDao dao = ArtsyDatabase.getDatabaseInstance(getApplication()).getArtworkDao();

            if (intent.hasExtra(ARTWORK_INTENT_SERVICE)) {
                final String token = intent.getStringExtra(ARTWORK_INTENT_SERVICE);

                new NetworkResourceWorkerThread<ArtworkResponse>(executor, new NetworkCallback() {
                    @Override
                    public void getNetworkStatus(Status status) {
                        //do nothing
                    }
                }, false){
                    @Override
                    protected void onFetchFailed() {
                        Log.v(TAG, "OnItemAtEndLoaded IntentService problem!");
                    }

                    @NonNull
                    @Override
                    protected Call<ArtworkResponse> createCall() {
                        return endpoint.getArtworks(token,LOADING_ARTWORK_SIZE);
                    }

                    @Override
                    protected void saveCallResult(@NonNull ArtworkResponse item) {
                        setNetworkResult(item,dao);
                    }
                };


            }
        }
    }

    @WorkerThread
    private void setNetworkResult(ArtworkResponse bodyResponse, @NonNull ArtworkDao dao){
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
                    if (nextFetch != null){
                        artwork.setNextPage(nextFetch);
                    }
                }
                dao.insertAll(artworks);
            }

        }
    }
}
