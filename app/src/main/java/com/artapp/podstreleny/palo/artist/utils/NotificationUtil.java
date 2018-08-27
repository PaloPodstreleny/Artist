package com.artapp.podstreleny.palo.artist.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.GlideRequest;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.ui.art.artworks.detail.ArtworkDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class NotificationUtil implements OnBitmapLoaded {

    private static final String CHANNEL_ID = "daily_notification";
    private static int UNIQUE_ID = 123456;

    private final Context context;
    private final NotificationCompat.Builder mBuilder;

    public NotificationUtil(Context context){
        this.context = context;
        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
    }



    public void fireNotification(Artwork artwork){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notification_channel_name);
            String description = context.getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if(notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, ArtworkDetail.class);
        intent.putExtra(ArtworkDetail.ARTWORK_DETAIL,artwork);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set title
        String title = "Daily motivation";
        if(artwork.hasTitle()) {
           title = artwork.getTitle();
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title)
                .setContentText("Check out this artwork!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if(artwork.hasThumbnail()){
            getBigPicture(context,artwork.getThumbnail());
        }


    }

    @Override
    public void onLoaded(Bitmap bitmap) {
        mBuilder.setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(UNIQUE_ID, mBuilder.build());
    }


    private void getBigPicture(Context context,@NonNull String thumbnail) {
        final GlideRequest<Bitmap> bitmap = GlideApp.with(context).asBitmap();
        bitmap.load(thumbnail).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                NotificationUtil.this.onLoaded(resource);
            }
        });

    }



}
