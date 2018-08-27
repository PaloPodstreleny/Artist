package com.artapp.podstreleny.palo.artist.repositories.artists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.TokenFetcher;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtistDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.IToken;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

public class ArtistRepository {

    private static ArtistRepository INSTANCE;

    private final IToken mTokenEndpoint;
    private final AppExecutor appExecutor;
    private final ArtistDao mArtistDao;
    private final ArtsyEndpoint mEndpoint;
    private final MutableLiveData<Status> liveStatus = new MutableLiveData<>();

    private ArtistRepository(AppExecutor appExecutor, ArtistDao dao, ArtsyEndpoint endpoint, IToken tokenEndpoint) {
        this.appExecutor = appExecutor;
        this.mArtistDao = dao;
        this.mEndpoint = endpoint;
        this.mTokenEndpoint = tokenEndpoint;

    }

    public static ArtistRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ArtistRepository.class) {
                INSTANCE = new ArtistRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getArtistDao(),
                        RetrofitProvider.getService(ArtsyEndpoint.class),
                        RetrofitProvider.getService(IToken.class)
                );
            }
        }
        return INSTANCE;
    }

    public LiveData<Resource<ArtysToken>> fetchToken() {
        return new TokenFetcher(mTokenEndpoint, appExecutor).asLiveData();
    }

    public LiveData<PagedList<Artist>> getArtists(final String token) {

        final int PAGE_SIZE = 20;
        final PagedList.Config mConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();

        final DataSource.Factory factory = mArtistDao.getArtists();
        final NetworkCallback callback = new NetworkCallback() {
            @Override
            public void getNetworkStatus(Status status) {
                liveStatus.postValue(status);
            }
        };

        final ArtistBoundCallback boundaryCallback = new ArtistBoundCallback(token, mArtistDao, appExecutor, mEndpoint, callback);
        final LiveData<PagedList<Artist>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }

    public LiveData<Status> getActualStatus(){
        return liveStatus;
    }





}
