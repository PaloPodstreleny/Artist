package com.artapp.podstreleny.palo.artist;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import com.artapp.podstreleny.palo.artist.network.Status;

public class LiveList<T> {
    private final LiveData<PagedList<T>> data;
    private final LiveData<Status> status;

    public LiveList(LiveData<PagedList<T>> data, LiveData<Status> status){
        this.data = data;
        this.status = status;
    }

    public LiveData<PagedList<T>>  getData() {
        return data;
    }

    public LiveData<Status> getStatus() {
        return status;
    }
}
