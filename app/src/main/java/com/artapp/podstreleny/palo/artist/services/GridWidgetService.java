package com.artapp.podstreleny.palo.artist.services;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.ArtsyDatabase;
import com.artapp.podstreleny.palo.artist.db.daos.ArtworkDao;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.ui.art.artworks.detail.ArtworkDetail;

import java.util.List;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplication());
    }
}



class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArtsyDatabase mDatabase;
    private List<Artwork> mArtworks;
    private Context context;

    public GridRemoteViewsFactory(Application application) {
        mDatabase = ArtsyDatabase.getDatabaseInstance(application);
        context = application;
    }


    @Override
    public void onCreate() { }

    //Caled on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        //Get data
        if (mDatabase != null) {
            final ArtworkDao dao = mDatabase.getArtworkDao();
            mArtworks = dao.getArtworksForWidget();
        }
    }

    @Override
    public void onDestroy() {
        mArtworks = null;
    }

    @Override
    public int getCount() {
        if (mArtworks == null) {
            return 0;
        } else {
            return mArtworks.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mArtworks == null || mArtworks.size() == 0) return null;

        final Artwork artwork = mArtworks.get(i);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.art_widget_list_item);


        //Set TextView
        final String title = artwork.getTitle();
        if(title != null){
            remoteViews.setTextViewText(R.id.artwork_title_widget, title);
        }

        Bundle extras = new Bundle();
        extras.putString(ArtworkDetail.ARTWORK_DETAIL,artwork.getId());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.artwork_title_widget, fillInIntent);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}