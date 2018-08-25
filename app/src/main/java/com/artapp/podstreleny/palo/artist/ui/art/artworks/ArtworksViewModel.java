package com.artapp.podstreleny.palo.artist.ui.art.artworks;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.repositories.ArtworkRepository;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

import java.util.List;

public class ArtworksViewModel extends AndroidViewModel {

    private ArtworkRepository mRepository = ArtworkRepository.getInstance(getApplication());

    private MutableLiveData<Boolean> shouldFetchToken = new MutableLiveData<>();
    private LiveData<Resource<ArtysToken>> token = Transformations.switchMap(shouldFetchToken, new Function<Boolean, LiveData<Resource<ArtysToken>>>() {
        @Override
        public LiveData<Resource<ArtysToken>> apply(Boolean input) {
           return mRepository.fetchToken();
        }
    });

    private MutableLiveData<String> tokenChanged = new MutableLiveData<>();
    private LiveData<Resource<List<Artwork>>> artworks = Transformations.switchMap(tokenChanged, new Function<String, LiveData<Resource<List<Artwork>>>>() {
        @Override
        public LiveData<Resource<List<Artwork>>> apply(String input) {
            return mRepository.getArtworks(input);
        }
    });

    public ArtworksViewModel(Application application){
        super(application);
    }

    public LiveData<Resource<List<Artwork>>> getArtworks(){
        return artworks;
    }

    public LiveData<Resource<ArtysToken>> getToken() {
        return token;
    }

    public void fetchToken(){
        final Boolean value = shouldFetchToken.getValue();
        if(value == null){
            shouldFetchToken.setValue(true);
        }else {
            shouldFetchToken.setValue(!value);
        }
    }

    public void setToken(String token){
        tokenChanged.setValue(token);
    }


}
