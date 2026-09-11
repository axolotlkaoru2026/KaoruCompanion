package com.kaoru.yalive;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class YaWidget extends AppWidgetProvider {
    public static final String ACTION_TAP = "com.kaoru.yalive.TAP_WIDGET";
    @Override public void onUpdate(Context c, AppWidgetManager m, int[] ids) {
        for(int id:ids){
            RemoteViews v=new RemoteViews(c.getPackageName(),R.layout.yaa_widget);
            Intent i=new Intent(c,YaWidget.class); i.setAction(ACTION_TAP);
            PendingIntent p=PendingIntent.getBroadcast(c, id, i, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
            v.setOnClickPendingIntent(R.id.widget_root,p); m.updateAppWidget(id,v);
        }
    }
    @Override public void onReceive(Context c, Intent i){
        super.onReceive(c,i);
        if(ACTION_TAP.equals(i.getAction())) YaWallpaperService.signalInteraction(c, "widget");
    }
}
