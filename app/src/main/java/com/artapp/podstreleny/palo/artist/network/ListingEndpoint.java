package com.artapp.podstreleny.palo.artist.network;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ListingEndpoint<Response> {

    @GET("artworks")
    Call<Response> getFirstLoadData(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size
    );

    @GET
    Call<Response> getNextPageData(
            @Url() String string,
            @Header("X-Xapp-Token") String token
    );

}
