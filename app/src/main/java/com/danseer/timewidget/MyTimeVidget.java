package com.danseer.timewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeVidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_time_vidget);

        views.setTextViewText(R.id.appwidget_text, getDate());

        for (int i = 0; i < appWidgetIds.length; i++) {
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        updateAppWidget(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, TimeService.class));
        } else {
            context.startService(new Intent(context, TimeService.class));
        }
    }

    @Override
    public void onDisabled(Context context) {
        context.stopService(new Intent(context, TimeService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        if (appWidgetIds.length == 0) {
            context.stopService(new Intent(context, TimeService.class));
        }
    }

    private static String getDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return dateFormat.format(new Date());

    }


}

