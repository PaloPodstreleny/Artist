//package com.artapp.podstreleny.palo.artist.repositories.shows;
//
//import android.arch.paging.PagedList;
//import android.support.annotation.NonNull;
//import android.support.annotation.WorkerThread;
//import android.util.Log;
//
//import com.artapp.podstreleny.palo.artist.AppExecutor;
//import com.artapp.podstreleny.palo.artist.db.entity.Gene;
//import com.artapp.podstreleny.palo.artist.db.entity.Show;
//import com.artapp.podstreleny.palo.artist.network.ListingEndpoint;
//import com.artapp.podstreleny.palo.artist.network.NetworkCallback;
//import com.artapp.podstreleny.palo.artist.network.NetworkResource;
//import com.artapp.podstreleny.palo.artist.network.api_responses.ImportantLink;
//import com.artapp.podstreleny.palo.artist.network.api_responses.Link;
//import com.artapp.podstreleny.palo.artist.network.api_responses.genes.GeneData;
//import com.artapp.podstreleny.palo.artist.network.api_responses.genes.GeneResponse;
//import com.artapp.podstreleny.palo.artist.repositories.genes.GeneRepository;
//
//import java.util.List;
//
//import retrofit2.Call;
//
//package com.artapp.podstreleny.palo.artist.repositories.genes;
//
//class GeneralBoundryCallback<GeneralResponse,Entity,Dao> extends PagedList.BoundaryCallback<Entity>{
//
//    private static final String TAG = GeneRepository.class.getSimpleName();
//    private static final int PREFETCH_SIZE = 50;
//    private AppExecutor executor;
//    private NetworkCallback callback;
//    private ListingEndpoint<GeneralResponse> endpoint;
//    private String token;
//    private Dao dao;
//
//    private boolean isLoaded;
//
//    public GeneralBoundryCallback(String token, Dao dao, AppExecutor executor, ListingEndpoint<GeneralResponse> endpoint,NetworkCallback callback) {
//        this.executor = executor;
//        this.endpoint = endpoint;
//        this.token = token;
//        this.dao = dao;
//        this.callback = callback;
//    }
//
//    @Override
//    public void onZeroItemsLoaded() {
//        if(isLoaded) return;
//        isLoaded = true;
//        new NetworkResource<GeneralResponse>(executor,callback,true){
//            @Override
//            protected void onFetchFailed() {
//                isLoaded = false;
//                Log.v(TAG,"ZeroItemsLoaded problem Gene!");
//            }
//
//            @NonNull
//            @Override
//            protected Call<GeneralResponse> createCall() {
//                return endpoint.getFirstLoadData(token,PREFETCH_SIZE);
//            }
//
//            @Override
//            protected void saveCallResult(@NonNull GeneralResponse item) {
//                isLoaded = false;
//                saveGeneData(item);
//
//            }
//        };
//
//    }
//
//    @Override
//    public void onItemAtEndLoaded(@NonNull final Entity itemAtEnd) {
//        if(isLoaded) return;
//        isLoaded = true;
//        new NetworkResource<GeneralResponse>(executor,callback,false){
//            @Override
//            protected void onFetchFailed() {
//                isLoaded = false;
//                Log.v(TAG,"onItemAtEndLoaded problem Gene !");
//            }
//
//            @NonNull
//            @Override
//            protected Call<GeneralResponse> createCall() {
//                return endpoint.getNextPageData(itemAtEnd.(),token);
//            }
//
//            @Override
//            protected void saveCallResult(@NonNull GeneResponse item) {
//                isLoaded = false;
//                saveGeneData(item);
//            }
//        };
//    }
//
//    @WorkerThread
//    private void saveGeneData(@NonNull GeneralResponse<Show> bodyResponse) {
//        final GeneData data = bodyResponse.getData();
//        final ImportantLink links = bodyResponse.getLinks();
//
//        if (data != null && links != null) {
//            final List<Gene> genes = bodyResponse.getData().getGenes();
//            if (genes != null) {
//                final String nextFetch = bodyResponse.getLinks().getNext().getHref();
//                for (int x = 0; x < genes.size(); x++) {
//                    final Gene gene = genes.get(x);
//                    final Link imageURL = genes.get(x).getLinks().getThumbnail();
//                    if (imageURL != null) {
//                        gene.setThumbnail(imageURL.getHref());
//                    }
//                    if(nextFetch != null) {
//                        gene.setNextPage(nextFetch);
//                    }
//                }
//                dao.insertAll(genes);
//            }
//
//        }
//
//    }
//
//}
