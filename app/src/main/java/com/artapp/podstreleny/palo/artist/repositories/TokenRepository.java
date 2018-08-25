package com.artapp.podstreleny.palo.artist.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.SomeFancyClass;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.responses.ApiErrorResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiSuccessResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiUnauthorizedResponse;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

import java.io.IOException;

import retrofit2.Response;


public class TokenRepository {

//    private static TokenRepository INSTANCE;
//
//    private AppExecutor appExecutor;
//    private ArtsyEndpoint tokenEndpoint;
//
//    private TokenRepository(ArtsyEndpoint tokenEndpoint, AppExecutor appExecutor){
//        this.tokenEndpoint = tokenEndpoint;
//        this.appExecutor = appExecutor;
//    }
//
//
//    public static TokenRepository getInstance() {
//        if(INSTANCE == null){
//            synchronized (TokenRepository.class){
//                INSTANCE = new TokenRepository(RetrofitProvider.getService(ArtsyEndpoint.class),new AppExecutor());
//            }
//        }
//        return INSTANCE;
//    }
//
//    public LiveData<Resource<ArtysToken>> fetchTokenFromAPI() {
//        return new SomeFancyClass(tokenEndpoint,appExecutor).asLiveData();
//    }



}
