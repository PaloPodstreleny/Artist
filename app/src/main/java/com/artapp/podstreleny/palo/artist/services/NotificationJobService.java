package com.artapp.podstreleny.palo.artist.services;

import android.content.Context;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.utils.NotificationUtil;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Random;

public class NotificationJobService extends JobService {

    private boolean isJobDone;

    @Override
    public boolean onStartJob(final JobParameters job) {

        final Context context = getApplication();
        final AppExecutor executor = AppExecutor.getInstance();
        final ArtsyDatabase database = ArtsyDatabase.getDatabaseInstance(getApplication());

        executor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final ArtworkDao dao = database.getArtworkDao();
                final int rows = dao.getNumberOfRows();
                final Random random = new Random();
                final Artwork artwork = dao.getRandomArtwork(random.nextInt(rows));

                new NotificationUtil(context).fireNotification(artwork);
                isJobDone = true;
                jobFinished(job,false);
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return isJobDone;
    }
}
