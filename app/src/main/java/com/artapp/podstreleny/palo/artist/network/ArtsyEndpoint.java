package com.artapp.podstreleny.palo.artist.network;

import com.artapp.podstreleny.palo.artist.network.api_responses.artists.ArtistResponse;
import com.artapp.podstreleny.palo.artist.network.api_responses.artwork.ArtworkResponse;
import com.artapp.podstreleny.palo.artist.network.api_responses.genes.GeneResponse;
import com.artapp.podstreleny.palo.artist.network.api_responses.shows.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ArtsyEndpoint {

    /* ARTWORKS */

    @GET("artworks")
    Call<ArtworkResponse> getArtworks(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size
    );

    @GET
    Call<ArtworkResponse> getNextArtworksPage(
            @Url() String string,
            @Header("X-Xapp-Token") String token
    );

    /* ARTIST */

    @GET("artists")
    Call<ArtistResponse> getArtists(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size,
            @Query("artworks") boolean artwork
    );

    @GET
    Call<ArtistResponse> getNextArtistPage(
            @Url() String string,
            @Header("X-Xapp-Token") String token
    );

    /* GENES */

    @GET("genes")
    Call<GeneResponse> getGenes(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size
    );

    @GET
    Call<GeneResponse> getNextGenePage(
            @Url() String string,
            @Header("X-Xapp-Token") String token
    );

    /* SHOWS */

    @GET("shows")
    Call<ShowResponse> getShows(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size
    );


    @GET("shows")
    Call<ShowResponse> getFilteredShow(
            @Header("X-Xapp-Token") String token,
            @Query("size") int size,
            @Query("status") String status
    );

    @GET
    Call<ShowResponse> getNextShow(
            @Url() String string,
            @Header("X-Xapp-Token") String token
    );
}


