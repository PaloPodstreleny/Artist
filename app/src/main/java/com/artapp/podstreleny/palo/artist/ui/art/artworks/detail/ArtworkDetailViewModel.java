package com.artapp.podstreleny.palo.artist.ui.art.artworks.detail;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.repositories.artworks.ArtworkDetailRepository;

import java.util.List;

public class ArtworkDetailViewModel extends AndroidViewModel {

    private ArtworkDetailRepository mRepository = ArtworkDetailRepository.getInstance(getApplication());
    private MutableLiveData<String> artworkID = new MutableLiveData<>();
    private MutableLiveData<String> token = new MutableLiveData<>();
    private String id;

    private LiveData<Artwork> artwork = Transformations.switchMap(artworkID, new Function<String, LiveData<Artwork>>() {
        @Override
        public LiveData<Artwork> apply(String input) {
            return mRepository.getArtwork(input);
        }
    });

    private LiveData<Resource<List<Artist>>> artists = Transformations.switchMap(token, new Function<String, LiveData<Resource<List<Artist>>>>() {
        @Override
        public LiveData<Resource<List<Artist>>> apply(String input) {
            return mRepository.getArtistsByArtwork(input,id);
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

    public LiveData<Resource<List<Artist>>> getArtworkArtists() {
        return artists;
    }

    public void setToken(String token, String id) {
        this.id = id;
        this.token.setValue(token);

    }
}
