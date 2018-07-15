package com.jkeiffertechnologies.athomereminders;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.content.Context;
import android.net.NetworkInfo.DetailedState;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kbecker on 7/14/2018.
 */

/*
* SUMMARY:
* All that this code does is send a request to the wifi server with the currently connected wifi ssid
* */

public class AtHomeReminderConnection {

    Context context;
    OkHttpClient client = new OkHttpClient();

    public AtHomeReminderConnection(Context context1) throws Exception {
        context = context1;
        start();
    }

    private String sendRequest() throws IOException {
        return run("http://keithloganbecker.000webhostapp.com/apps/wifi/?wifi=" + getWifiName(context));
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void start()
    {
        new Thread(new Runnable() {
            public void run() {
                try {
                    sendRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == DetailedState.CONNECTED || state == DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

}
