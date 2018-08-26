package com.artapp.podstreleny.palo.artist.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.LiveList;
import com.artapp.podstreleny.palo.artist.TokenFetcher;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.IToken;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;


public class ArtworkRepository extends PagedList.BoundaryCallback<Artwork> {

    private static ArtworkRepository INSTANCE;

    private final IToken mTokenEndpoint;
    private final AppExecutor appExecutor;
    private final ArtworkDao mArtworkDao;
    private final ArtsyEndpoint mEndpoint;
    private final MutableLiveData<Status> liveStatus = new MutableLiveData<>();


    private ArtworkRepository(AppExecutor appExecutor, ArtworkDao dao, ArtsyEndpoint endpoint, IToken tokenEndpoint) {
        this.appExecutor = appExecutor;
        this.mArtworkDao = dao;
        this.mEndpoint = endpoint;
        this.mTokenEndpoint = tokenEndpoint;

    }

    public static ArtworkRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ArtworkRepository.class) {
                INSTANCE = new ArtworkRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getArtworkDao(),
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

    public LiveData<Status> getActualStatus(){
        return liveStatus;
    }


    public LiveData<PagedList<Artwork>> getArtworks(final String token) {

        final int PAGE_SIZE = 20;
        final PagedList.Config mConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();

        final DataSource.Factory factory = mArtworkDao.getArtworks();
        final NetworkCallback callback = new NetworkCallback() {
            @Override
            public void getNetworkStatus(Status status) {
                liveStatus.postValue(status);
            }
        };

        final ArtworkBoundaryCallback boundaryCallback = new ArtworkBoundaryCallback(token, mArtworkDao, appExecutor, mEndpoint, callback);
        final LiveData<PagedList<Artwork>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }


    @Override
    public void onItemAtEndLoaded(@NonNull Artwork itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);

    }
}
