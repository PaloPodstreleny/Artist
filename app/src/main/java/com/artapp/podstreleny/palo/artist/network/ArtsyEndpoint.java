package com.artapp.podstreleny.palo.artist.network;

import android.arch.lifecycle.LiveData;

import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ArtsyEndpoint extends IToken {

    @GET("artworks")
    LiveData<ApiResponse<ArtworkResponse>> getArtworks(@Header("X-Xapp-Token") String token);
}


