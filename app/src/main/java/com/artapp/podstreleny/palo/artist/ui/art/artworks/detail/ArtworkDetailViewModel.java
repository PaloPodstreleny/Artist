package com.artapp.podstreleny.palo.artist.ui.art.artworks.detail;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.repositories.artworks.ArtworkDetailRepository;

public class ArtworkDetailViewModel extends AndroidViewModel {

    private ArtworkDetailRepository mRepository = ArtworkDetailRepository.getInstance(getApplication());
    private MutableLiveData<String> artworkID = new MutableLiveData<>();

    private LiveData<Artwork> artwork = Transformations.switchMap(artworkID, new Function<String, LiveData<Artwork>>() {
        @Override
        public LiveData<Artwork> apply(String input) {
          return mRepository.getArtwork(input);
        }
    });


    public ArtworkDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setArtworkID(String id){
        artworkID.setValue(id);
    }

    public LiveData<Artwork> getArtwork() {
        return artwork;
    }
}
