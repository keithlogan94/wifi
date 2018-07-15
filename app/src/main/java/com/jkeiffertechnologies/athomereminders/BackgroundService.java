package com.jkeiffertechnologies.athomereminders;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kbecker on 7/15/2018.
 */

public class BackgroundService extends Service {
    public BackgroundService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Created.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Starting Service.");
        WebsiteActiveWifiUpdater websiteActiveWifiUpdater = new WebsiteActiveWifiUpdater(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Binded.");
        return null;
    }
}
