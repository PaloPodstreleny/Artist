package com.artapp.podstreleny.palo.artist;

import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ArtsyEndpoint  {

    @POST("https://api.artsy.net/api/tokens/xapp_token")
    @FormUrlEncoded
    Call<ArtysToken> getData(@Field("client_id") String clientId, @Field("client_secret") String clientSecret);

    @GET("https://api.artsy.net/api/artists/andy-warhol")
    Call<ResponseBody> getArtist(@Header("X-Xapp-Token") String token);


}
