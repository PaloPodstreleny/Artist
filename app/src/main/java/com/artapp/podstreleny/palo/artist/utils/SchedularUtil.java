package com.artapp.podstreleny.palo.artist.utils;

import android.content.Context;

import com.artapp.podstreleny.palo.artist.services.NotificationJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class SchedularUtil {

    private static final String NOTIFICATION_JOB = "notification_job";
    private static SchedularUtil INSTANCE;
    private FirebaseJobDispatcher mDispetcher;

    private SchedularUtil(Context context){
        mDispetcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public static SchedularUtil getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SchedularUtil.class){
                INSTANCE = new SchedularUtil(context);
            }
        }
        return INSTANCE;
    }

    public void scheduleJobs(){
        scheduleNotificationJob();
    }

    private void scheduleNotificationJob (){

        Job job = mDispetcher.newJobBuilder()
                // the JobService that will be called
                .setService(NotificationJobService.class)
                // uniquely identifies the job
                .setTag(NOTIFICATION_JOB)
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 10))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK

                )
                .build();
        mDispetcher.schedule(job);
    }
}
