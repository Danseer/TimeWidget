package com.danseer.timewidget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class TimeService extends Service {

    Handler handler;
    Runnable runnable;
    public static final Integer GET_ORDERS_RATE = 1000;

    public TimeService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacks(runnable);
        getUpdate();
        return START_STICKY;
    }

    public void getUpdate() {
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                updateTimeWidget();
            }
        }, GET_ORDERS_RATE);
    }


    private void updateTimeWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int ids[] = appWidgetManager.getAppWidgetIds(new ComponentName(this.getApplicationContext().getPackageName(), MyTimeVidget.class.getName()));
        MyTimeVidget.updateAppWidget(this.getApplicationContext(), appWidgetManager, ids);

        getUpdate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeCallbacks(runnable);

    }

}
