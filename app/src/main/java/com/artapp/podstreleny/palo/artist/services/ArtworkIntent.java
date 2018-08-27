package com.artapp.podstreleny.palo.artist.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class ArtworkIntent extends IntentService {


    private static final String ARTWORK_INTENT_SERVICE = "artwork_intent_service";

    public ArtworkIntent(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.hasExtra(ARTWORK_INTENT_SERVICE)) {
            String nextPage = intent.getStringExtra(ARTWORK_INTENT_SERVICE);


            //TODO finish this tomorrow
//            new NetworkResource<ArtworkResponse>() {
//                @Override
//                protected void onFetchFailed() {
//                    isLoaded = false;
//                    Log.v(TAG, "OnItemAtEndLoaded network problem!");
//                }
//
//                @NonNull
//                @Override
//                protected Call<ArtworkResponse> createCall() {
//                    return endpoint.getNextArtworksPage(itemAtEnd.getNextPage(), token);
//                }
//
//                @Override
//                protected void saveCallResult(@NonNull ArtworkResponse item) {
//                    isLoaded = false;
//                    saveArtworkData(item);
//                }
//            };

        }
    }
}
