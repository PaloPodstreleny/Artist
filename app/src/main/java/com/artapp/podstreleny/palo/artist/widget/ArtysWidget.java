package com.artapp.podstreleny.palo.artist.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.services.GridWidgetService;
import com.artapp.podstreleny.palo.artist.ui.art.artworks.detail.ArtworkDetail;

/**
 * Implementation of App Widget functionality.
 */
public class ArtysWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getArtGridRemoteViews(context);
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    private static RemoteViews getArtGridRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.art_widget_provider);

        Intent intent = new Intent(context,GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_gv,intent);

        Intent appIntent = new Intent(context, ArtworkDetail.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(appIntent);

        PendingIntent appPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_gv,appPendingIntent);
        views.setEmptyView(R.id.widget_gv,R.id.empty_view);

        return views;

    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        onUpdateWidget(context,appWidgetManager,appWidgetIds);
    }


    public static void onUpdateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetsIds){
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetsIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
