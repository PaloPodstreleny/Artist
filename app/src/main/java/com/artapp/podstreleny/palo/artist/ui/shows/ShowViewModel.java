package com.artapp.podstreleny.palo.artist.ui.shows;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;

import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.repositories.shows.ShowRepository;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;


public class ShowViewModel extends AndroidViewModel {

    private ShowRepository mRepository = ShowRepository.getInstance(getApplication());
    private String type = ShowPeriod.DEFAULT;

    private MutableLiveData<Boolean> shouldFetchToken = new MutableLiveData<>();
    private LiveData<Resource<ArtysToken>> token = Transformations.switchMap(shouldFetchToken, new Function<Boolean, LiveData<Resource<ArtysToken>>>() {
        @Override
        public LiveData<Resource<ArtysToken>> apply(Boolean input) {
            return mRepository.fetchToken();
        }
    });

    private MutableLiveData<String> tokenChanged = new MutableLiveData<>();
    private LiveData<PagedList<Show>> shows = Transformations.switchMap(tokenChanged, new Function<String, LiveData<PagedList<Show>>>() {
        @Override
        public LiveData<PagedList<Show>> apply(String input) {
            if(type.equals(ShowPeriod.DEFAULT)){
                return mRepository.getShows(input);
            }else if(type.equals(ShowPeriod.UPCOMING)){
                return mRepository.getFilteredShows(input,type);
            }else if (type.equals(ShowPeriod.RUNNING)){
                return mRepository.getFilteredShows(input,type);
            }else if (type.equals(ShowPeriod.CLOSED)){
                return mRepository.getFilteredShows(input,type);
            }else {
                throw new IllegalArgumentException("Undefined type!");
            }

        }
    });

    private LiveData<Status> status = Transformations.switchMap(tokenChanged, new Function<String, LiveData<Status>>() {
        @Override
        public LiveData<Status> apply(String input) {
            return mRepository.getActualStatus();
        }
    });

    public void setFilter(String filter){
        this.type = filter;
    }

    public ShowViewModel(Application application){
        super(application);
    }

    public LiveData<PagedList<Show>> getShows() {
        return shows;
    }

    public LiveData<Resource<ArtysToken>> getToken() {
        return token;
    }

    public LiveData<Status> getStatus(){return status;}

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
