package com.artapp.podstreleny.palo.artist.network;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;


import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.network.responses.ApiEmptyResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiErrorResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiSuccessResponse;
import com.artapp.podstreleny.palo.artist.network.responses.ApiUnauthorizedResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkResource<RequestType> {

    private static final int EMPTY_RESPONSE = 204;
    private static final int UNAUTHORIZED_RESPONSE = 401;

    public NetworkResource(final AppExecutor executor, final NetworkCallback callback, final boolean isFirstLoad) {
        Call<RequestType> apiResponse = createCall();
        callback.getNetworkStatus(Status.LOADING);
        apiResponse.enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(final Call<RequestType> call, final Response<RequestType> response) {
                executor.diskIO().execute(new Runnable() {

                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            RequestType body = response.body();
                            if (body == null || response.code() == EMPTY_RESPONSE) {
                                callback.getNetworkStatus(Status.EMPTY);
                            } else {
                                saveCallResult(body);
                                callback.getNetworkStatus(Status.SUCCESS);
                            }
                        } else if (response.code() == UNAUTHORIZED_RESPONSE) {
                            onFetchFailed();
                            callback.getNetworkStatus(Status.UNAUTHORIZED);
                        } else {
                            onFetchFailed();
                            if(isFirstLoad) {
                                callback.getNetworkStatus(Status.FIRST_LOAD_ERROR);
                            }else {
                                callback.getNetworkStatus(Status.ERROR);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                if(isFirstLoad){
                    callback.getNetworkStatus(Status.FIRST_LOAD_ERROR);
                }else {
                    callback.getNetworkStatus(Status.ERROR);
                }
            }
        });
    }



    @WorkerThread
    protected abstract void onFetchFailed();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);




}
