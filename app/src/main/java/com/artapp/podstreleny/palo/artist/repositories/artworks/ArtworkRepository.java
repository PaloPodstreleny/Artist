package com.artapp.podstreleny.palo.artist.repositories.artworks;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.ComponentName;
import android.content.Context;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.R;
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
import com.artapp.podstreleny.palo.artist.widget.ArtysWidget;


public class ArtworkRepository  {

    private static ArtworkRepository INSTANCE;

    private final IToken mTokenEndpoint;
    private final AppExecutor appExecutor;
    private final ArtworkDao mArtworkDao;
    private final ArtsyEndpoint mEndpoint;
    private final MutableLiveData<Status> liveStatus = new MutableLiveData<>();
    private final Context context;


    private ArtworkRepository(AppExecutor appExecutor, ArtworkDao dao, ArtsyEndpoint endpoint, IToken tokenEndpoint,Context context) {
        this.appExecutor = appExecutor;
        this.mArtworkDao = dao;
        this.mEndpoint = endpoint;
        this.mTokenEndpoint = tokenEndpoint;
        this.context = context;

    }

    public static ArtworkRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ArtworkRepository.class) {
                INSTANCE = new ArtworkRepository(
                        AppExecutor.getInstance(),
                        ArtsyDatabase.getDatabaseInstance(application).getArtworkDao(),
                        RetrofitProvider.getService(ArtsyEndpoint.class),
                        RetrofitProvider.getService(IToken.class),
                        application
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
                if(status == Status.SUCCESS){
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(context, ArtysWidget.class));
                    manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_gv);
                    ArtysWidget.onUpdateWidget(context, manager, appWidgetIds);
                }
            }
        };

        final ArtworkBoundaryCallback boundaryCallback = new ArtworkBoundaryCallback(token, mArtworkDao, appExecutor, mEndpoint, callback);
        final LiveData<PagedList<Artwork>> data = new LivePagedListBuilder<>(factory, mConfig).setBoundaryCallback(boundaryCallback).build();
        return data;
    }


}
