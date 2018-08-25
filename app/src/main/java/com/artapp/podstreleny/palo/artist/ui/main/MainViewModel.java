package com.artapp.podstreleny.palo.artist.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.repositories.TokenRepository;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

public class MainViewModel extends ViewModel {

//    private MutableLiveData<Boolean> shouldFetch = new MutableLiveData<>();
//
//    private final TokenRepository mTokenRepository = TokenRepository.getInstance();
//    public final LiveData<Resource<ArtysToken>> getToken = Transformations.switchMap(shouldFetch, new Function<Boolean, LiveData<Resource<ArtysToken>>>() {
//        @Override
//        public LiveData<Resource<ArtysToken>> apply(Boolean input) {
//            return mTokenRepository.fetchTokenFromAPI();
//        }
//    });
//
//    public void fetchToken(){
//        if(shouldFetch.getValue() != null) {
//            shouldFetch.setValue(!shouldFetch.getValue());
//        }else {
//            shouldFetch.setValue(true);
//        }
//    }

}
