package com.artapp.podstreleny.palo.artist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.artapp.podstreleny.palo.artist.network.ArtsyTokenEndpoint;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.responses.ApiErrorResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiSuccessResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiUnauthorizedResponse;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

import java.io.IOException;

import retrofit2.Response;

public  class SomeFancyClass {


    private final MediatorLiveData<Resource<ArtysToken>> result = new MediatorLiveData<>();
    private final ArtysToken emptyToken = null;
    final MutableLiveData<Resource<ArtysToken>> data = new MutableLiveData<>();

    public SomeFancyClass(final ArtsyTokenEndpoint tokenEndpoint,AppExecutor appExecutor){
        fetchToken(appExecutor,tokenEndpoint);
        result.addSource(data, new Observer<Resource<ArtysToken>>() {
            @Override
            public void onChanged(@Nullable Resource<ArtysToken> tokenResource) {
                result.removeSource(data);
                result.setValue(tokenResource);
            }
        });

    }

    private void fetchToken(AppExecutor appExecutor, final ArtsyTokenEndpoint tokenEndpoint){
        appExecutor.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                data.postValue(Resource.loading(emptyToken));
                try {
                    Response<ArtysToken> response = tokenEndpoint.getData(ApiKey.CLIENT_ID, ApiKey.CLIENT_SECRET).execute();
                    ApiResponse<ArtysToken> apiResponse = new ApiResponse<ArtysToken>().create(response);
                    if (apiResponse instanceof ApiSuccessResponse) {
                        final ApiSuccessResponse<ArtysToken> successResponse = (ApiSuccessResponse<ArtysToken>) apiResponse;
                        data.postValue(Resource.success(successResponse.getBody()));
                    } else if (apiResponse instanceof ApiErrorResponse) {
                        final ApiErrorResponse<ArtysToken> errorResponse = (ApiErrorResponse<ArtysToken>) apiResponse;
                        data.postValue(Resource.error(errorResponse.getErrorMessage(),emptyToken));
                    } else if (apiResponse instanceof ApiUnauthorizedResponse) {
                        data .postValue(Resource.unauthorized(emptyToken));
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                    data.postValue(Resource.error(exception.getMessage(),emptyToken));
                }
            }
        });
    }

    public LiveData<Resource<ArtysToken>> asLiveData(){
        return result;
    }
}
