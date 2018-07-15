package com.jkeiffertechnologies.athomereminders;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kbecker on 7/14/2018.
 */

public class ExampleService extends Service {

    private final int minuteInterval = 1;

    public ExampleService() {
        super();
    }

    public void runService() throws Exception {
        AtHomeReminderConnection homeReminderConnection = new AtHomeReminderConnection(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
        * SUMMARY
        * ALL THAT THIS CODE DOES
        * this code basically initializes the AtHomeReminderConnection Class every minuteInterval
        * */

        final Handler handler = new Handler();
        final int delay = 1000*60*this.minuteInterval;
        handler.postDelayed(new Runnable(){
            public void run(){
                try {
                    runService();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
        return Service.START_STICKY_COMPATIBILITY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
