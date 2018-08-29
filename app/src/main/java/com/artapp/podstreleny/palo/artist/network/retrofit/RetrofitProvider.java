package com.artapp.podstreleny.palo.artist.network.retrofit;


import com.artapp.podstreleny.palo.artist.utils.UrlUril;

import java.lang.reflect.ParameterizedType;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(UrlUril.RETROFIT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(new LiveDataCallAdapterFactory());

    private static Retrofit sRetrofit = sBuilder.build();

    public static <T> T getService(Class<T> data) {
        return sRetrofit.create(data);
    }


}

