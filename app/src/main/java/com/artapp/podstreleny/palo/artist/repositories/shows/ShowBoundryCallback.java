package com.artapp.podstreleny.palo.artist.repositories.shows;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.daos.ShowDao;
import com.artapp.podstreleny.palo.artist.db.entity.Gene;
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.NetworkResource;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.shows.ShowData;
import com.artapp.podstreleny.palo.artist.network.api_responses.shows.ShowResponse;
import com.artapp.podstreleny.palo.artist.ui.shows.ShowPeriod;

import java.util.List;

import retrofit2.Call;

class ShowBoundryCallback extends PagedList.BoundaryCallback<Show>{

    private static final String TAG = ShowBoundryCallback.class.getSimpleName();
    private static final int PREFETCH_SIZE = 50;
    private AppExecutor executor;
    private NetworkCallback callback;
    private ArtsyEndpoint endpoint;
    private String token;
    private ShowDao dao;
    private String filter;

    private boolean isLoaded;

    public ShowBoundryCallback(String token, ShowDao dao, AppExecutor executor, ArtsyEndpoint endpoint,NetworkCallback callback) {
        this.executor = executor;
        this.endpoint = endpoint;
        this.token = token;
        this.dao = dao;
        this.callback = callback;
    }

    public ShowBoundryCallback(String token, ShowDao dao, AppExecutor executor, ArtsyEndpoint endpoint,NetworkCallback callback,String filter) {
        this.executor = executor;
        this.endpoint = endpoint;
        this.token = token;
        this.dao = dao;
        this.callback = callback;
        this.filter = filter;
    }

    @Override
    public void onZeroItemsLoaded() {
        if(isLoaded) return;
        isLoaded = true;
        new NetworkResource<ShowResponse>(executor,callback,true){
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"ZeroItemsLoaded problem Gene!");
            }

            @NonNull
            @Override
            protected Call<ShowResponse> createCall() {
                if(filter == null) {
                    return endpoint.getShows(token, PREFETCH_SIZE);
                }else if(filter.equals(ShowPeriod.CLOSED)) {
                    return endpoint.getShows(token, PREFETCH_SIZE);
                }else if(filter.equals(ShowPeriod.RUNNING)){
                    return endpoint.getShows(token, PREFETCH_SIZE);
                }else if(filter.equals(ShowPeriod.UPCOMING)){
                    return endpoint.getShows(token, PREFETCH_SIZE);
                }else {
                    throw new IllegalArgumentException("Wrong ShowPeriod defined!");
                }
            }

            @Override
            protected void saveCallResult(@NonNull ShowResponse item) {
                isLoaded = false;
                saveGeneData(item);

            }
        };

    }

    @Override
    public void onItemAtEndLoaded(@NonNull final Show itemAtEnd) {
        if(isLoaded) return;
        isLoaded = true;
        new NetworkResource<ShowResponse>(executor,callback,false){
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG,"onItemAtEndLoaded problem Gene !");
            }

            @NonNull
            @Override
            protected Call<ShowResponse> createCall() {
                return endpoint.getNextShow(itemAtEnd.getNextPage(),token);
            }

            @Override
            protected void saveCallResult(@NonNull ShowResponse item) {
                isLoaded = false;
                saveGeneData(item);
            }
        };
    }

    @WorkerThread
    private void saveGeneData(@NonNull ShowResponse bodyResponse) {
        final ShowData data = bodyResponse.getData();
        final ImportantLink links = bodyResponse.getLinks();

        if (data != null && links != null) {
            final List<Show> shows = bodyResponse.getData().getShows();
            if (shows != null) {
                final String nextFetch = bodyResponse.getLinks().getNext().getHref();
                for (int x = 0; x < shows.size(); x++) {
                    final Show show = shows.get(x);
                    final Link imageURL = shows.get(x).getLinks().getThumbnail();
                    if (imageURL != null) {
                        show.setThumbnail(imageURL.getHref());
                    }
                    if(nextFetch != null) {
                        show.setNextPage(nextFetch);
                    }
                }
                dao.insertAll(shows);
            }

        }

    }

}
