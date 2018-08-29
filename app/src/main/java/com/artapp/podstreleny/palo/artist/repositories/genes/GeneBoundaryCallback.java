package com.artapp.podstreleny.palo.artist.repositories.genes;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.daos.GeneDao;
import com.artapp.podstreleny.palo.artist.db.entity.Gene;
import com.artapp.podstreleny.palo.artist.network.ArtsyEndpoint;
import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
import com.artapp.podstreleny.palo.artist.network.NetworkResource;
import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
import com.artapp.podstreleny.palo.artist.network.api_responses.genes.GeneData;
import com.artapp.podstreleny.palo.artist.network.api_responses.genes.GeneResponse;

import java.util.List;

import retrofit2.Call;

class GeneBoundaryCallback extends PagedList.BoundaryCallback<Gene> {

    private static final String TAG = GeneBoundaryCallback.class.getSimpleName();
    private static final int PREFETCH_SIZE = 50;
    private AppExecutor executor;
    private NetworkCallback callback;
    private ArtsyEndpoint endpoint;
    private String token;
    private GeneDao dao;

    private boolean isLoaded;

    public GeneBoundaryCallback(String token, GeneDao dao, AppExecutor executor, ArtsyEndpoint endpoint, NetworkCallback callback) {
        this.executor = executor;
        this.endpoint = endpoint;
        this.token = token;
        this.dao = dao;
        this.callback = callback;
    }

    @Override
    public void onZeroItemsLoaded() {
        if (isLoaded) return;
        isLoaded = true;
        new NetworkResource<GeneResponse>(executor, callback, true) {
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG, "Gene onZeroItemsLoaded network error!!");
            }

            @NonNull
            @Override
            protected Call<GeneResponse> createCall() {
                return endpoint.getGenes(token, PREFETCH_SIZE);
            }

            @Override
            protected void saveCallResult(@NonNull GeneResponse item) {
                isLoaded = false;
                saveGeneData(item);

            }
        };

    }

    @Override
    public void onItemAtEndLoaded(@NonNull final Gene itemAtEnd) {
        if (isLoaded) return;
        isLoaded = true;
        new NetworkResource<GeneResponse>(executor, callback, false) {
            @Override
            protected void onFetchFailed() {
                isLoaded = false;
                Log.v(TAG, "Gene onItemAtEndLoaded network error!");
            }

            @NonNull
            @Override
            protected Call<GeneResponse> createCall() {
                return endpoint.getNextGenePage(itemAtEnd.getNextPage(), token);
            }

            @Override
            protected void saveCallResult(@NonNull GeneResponse item) {
                isLoaded = false;
                saveGeneData(item);
            }
        };
    }

    @WorkerThread
    private void saveGeneData(@NonNull GeneResponse bodyResponse) {
        final GeneData data = bodyResponse.getData();
        final ImportantLink links = bodyResponse.getLinks();

        if (data != null && links != null) {
            final List<Gene> genes = bodyResponse.getData().getGenes();
            if (genes != null) {
                final String nextFetch = bodyResponse.getLinks().getNext().getHref();
                for (int x = 0; x < genes.size(); x++) {
                    final Gene gene = genes.get(x);
                    final Link imageURL = genes.get(x).getLinks().getThumbnail();
                    if (imageURL != null) {
                        gene.setThumbnail(imageURL.getHref());
                    }
                    if (nextFetch != null) {
                        gene.setNextPage(nextFetch);
                    }
                }
                dao.insertAll(genes);
            }

        }

    }

}
