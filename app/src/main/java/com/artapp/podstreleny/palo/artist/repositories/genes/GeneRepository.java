package com.artapp.podstreleny.palo.artist.repositories.genes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.TokenFetcher;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.GeneDao;
import com.artapp.podstreleny.palo.artist.db.entity.Gene;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.IToken;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.network.retrofit.RetrofitProvider;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;

public class GeneRepository {
    private static GeneRepository INSTANCE;

    private final IToken mTokenEndpoint;
    private final AppExecutor appExecutor;
    private final GeneDao mGeneDao;
    private final ArtsyEndpoint mEndpoint;
    private final MutableLiveData<Status> liveStatus = new MutableLiveData<>();

    private GeneRepository(AppExecutor appExecutor, GeneDao dao, ArtsyEndpoint endpoint, IToken tokenEndpoint) {
        this.appExecutor = appExecutor;
        this.mGeneDao = dao;
        this.mEndpoint = endpoint;
        this.mTokenEndpoint = tokenEndpoint;

    }

    public static GeneRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (GeneRepository.class) {
                INSTANCE = new GeneRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getGeneDao(),
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


    public LiveData<PagedList<Gene>> getGenes(final String token) {

        final int PAGE_SIZE = 20;
        final PagedList.Config mConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();

        final DataSource.Factory factory = mGeneDao.getGenes();
        final NetworkCallback callback = new NetworkCallback() {
            @Override
            public void getNetworkStatus(Status status) {
                liveStatus.postValue(status);
            }
        };

        final GeneBoundaryCallback boundaryCallback = new GeneBoundaryCallback(token, mGeneDao, appExecutor, mEndpoint, callback);
        final LiveData<PagedList<Gene>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }


}
