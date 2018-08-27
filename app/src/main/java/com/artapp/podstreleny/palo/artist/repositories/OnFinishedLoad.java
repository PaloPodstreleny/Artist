package com.artapp.podstreleny.palo.artist.repositories;

import android.support.annotation.WorkerThread;

@WorkerThread
public interface OnFinishedLoad {
    void finished(String nextPage);
}
