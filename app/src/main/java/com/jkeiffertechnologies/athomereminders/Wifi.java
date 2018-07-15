package com.jkeiffertechnologies.athomereminders;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by kbecker on 7/14/2018.
 */

public class Wifi {

    String wifiName = null;

    public Wifi(Context context) {
        wifiName = cleanWifiName(getWifiName(context));
        System.out.println(wifiName);
    }

    private String cleanWifiName(String wifiName) {
        String newWifiName = "";
        for (int i = 0; i < wifiName.length(); ++i) {
            if (wifiName.charAt(i) != '"') newWifiName += wifiName.charAt(i);
        }
        return newWifiName;
    }

    private String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

    public String getWifiName()
    {
        return wifiName;
    }
}
