package com.artapp.podstreleny.palo.artist.services;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

import com.artapp.podstreleny.palo.artist.AppExecutor;
import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.utils.NotificationUtil;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Random;

public class NotificationJobService extends JobService {

    private boolean shouldRetry = true;

    @Override
    public boolean onStartJob(final JobParameters job) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        if(preferences.contains(getString(R.string.preferences_notification_key))){
            if(!preferences.getBoolean(getString(R.string.preferences_notification_key),false)){
                shouldRetry = false;
                jobFinished(job,false);
                return false;
            }
        }

        final AppExecutor executor = AppExecutor.getInstance();
        final ArtsyDatabase database = ArtsyDatabase.getDatabaseInstance(getApplication());

        executor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final ArtworkDao dao = database.getArtworkDao();

                final int rows = dao.getNumberOfRows();

                //If there are no data return
                if (rows == 0){
                    jobFinished(job,true);
                    shouldRetry = true;
                    return;
                }

                final Random random = new Random();
                final Artwork artwork = dao.getRandomArtwork(random.nextInt(rows));

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.with(getBaseContext()).asBitmap().load(artwork.getThumbnail()).into(new SimpleTarget<Bitmap>(){
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                NotificationUtil.fireNotification(getBaseContext(),artwork,resource);
                                shouldRetry = false;
                                jobFinished(job,false);
                            }
                        });
                    }
                });

            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return shouldRetry;
    }
}
