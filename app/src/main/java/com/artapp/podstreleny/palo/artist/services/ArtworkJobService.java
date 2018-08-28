package com.artapp.podstreleny.palo.artist.services;
import android.content.Intent;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ArtworkJobService extends JobService {


    @Override
    public boolean onStartJob(final JobParameters job) {

        AppExecutor executor = AppExecutor.getInstance();
        final ArtsyDatabase database = ArtsyDatabase.getDatabaseInstance(getApplication());

        executor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
               final String nextPage = database.getArtworkDao().getNextPage();
               Intent intent = new Intent(getApplicationContext(),ArtworkIntent.class);
               intent.putExtra(ArtworkIntent.ARTWORK_INTENT_SERVICE,nextPage);
               getApplication().startService(intent) ;
               jobFinished(job,false);
            }
        });

        return false;

    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
