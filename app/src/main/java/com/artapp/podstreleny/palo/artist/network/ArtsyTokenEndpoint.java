package com.artapp.podstreleny.palo.artist.network;

import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ArtsyTokenEndpoint {

    @POST("tokens/xapp_token")
    @FormUrlEncoded
    Call<ArtysToken> getData(@Field("client_id") String clientId, @Field("client_secret") String clientSecret);

}
