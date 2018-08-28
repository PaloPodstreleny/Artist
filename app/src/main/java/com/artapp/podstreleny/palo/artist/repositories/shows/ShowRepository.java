package com.artapp.podstreleny.palo.artist.repositories.shows;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.TokenFetcher;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ShowDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.IToken;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;
import com.artapp.podstreleny.palo.artist.ui.shows.ShowPeriod;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;



public class ShowRepository {
    private static ShowRepository INSTANCE;

    private final IToken mTokenEndpoint;
    private final AppExecutor appExecutor;
    private final ShowDao mShowDao;
    private final ArtsyEndpoint mEndpoint;
    private final MutableLiveData<Status> liveStatus = new MutableLiveData<>();

    private ShowRepository(AppExecutor appExecutor, ShowDao dao, ArtsyEndpoint endpoint, IToken tokenEndpoint) {
        this.appExecutor = appExecutor;
        this.mShowDao = dao;
        this.mEndpoint = endpoint;
        this.mTokenEndpoint = tokenEndpoint;

    }

    public static ShowRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ShowRepository.class) {
                INSTANCE = new ShowRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getShowDao(),
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

    public LiveData<Status> getActualStatus() {
        return liveStatus;
    }


    public LiveData<PagedList<Show>> getShows(final String token) {

        final int PAGE_SIZE = 20;
        final PagedList.Config mConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();

        final DataSource.Factory factory = mShowDao.getShows();
        final NetworkCallback callback = new NetworkCallback() {
            @Override
            public void getNetworkStatus(Status status) {
                liveStatus.postValue(status);
            }
        };

        final ShowBoundryCallback boundaryCallback = new ShowBoundryCallback(token, mShowDao, appExecutor, mEndpoint, callback);
        final LiveData<PagedList<Show>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }

    public LiveData<PagedList<Show>> getFilteredShows(final String token, String filter) {

        final int PAGE_SIZE = 20;
        final PagedList.Config mConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();

        final DataSource.Factory factory = mShowDao.getFilteredShows(filter);
        final NetworkCallback callback = new NetworkCallback() {
            @Override
            public void getNetworkStatus(Status status) {
                liveStatus.postValue(status);
            }
        };


        final ShowBoundryCallback boundaryCallback = new ShowBoundryCallback(token, mShowDao, appExecutor, mEndpoint, callback,filter);
        final LiveData<PagedList<Show>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }




}
