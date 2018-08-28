package com.artapp.podstreleny.palo.artist.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.artapp.podstreleny.palo.artist.R;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ArtworkJobService extends JobService {


    @Override
    public boolean onStartJob(final JobParameters job) {

        final SharedPreferences preferences = getSharedPreferences(getString(R.string.token_file_key), Context.MODE_PRIVATE);
        if (preferences != null && preferences.contains(getString(R.string.token_entry_value))) {
            final String token = preferences.getString(getString(R.string.token_entry_value), null);
            if (token != null) {
                Intent intent = new Intent(getApplicationContext(), ArtworkIntent.class);
                intent.putExtra(ArtworkIntent.ARTWORK_INTENT_SERVICE, token);
                getApplication().startService(intent);
                jobFinished(job, false);
            }
        } else {
            jobFinished(job, true);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
