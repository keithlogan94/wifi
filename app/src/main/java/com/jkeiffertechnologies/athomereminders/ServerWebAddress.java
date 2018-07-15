package com.jkeiffertechnologies.athomereminders;

import android.content.Context;

/**
 * Created by kbecker on 7/14/2018.
 */
public class ServerWebAddress {

    private String url = "";
    Wifi wifi = null;

    public ServerWebAddress(Context context)
    {
        wifi = new Wifi(context);
        url = "http://keithloganbecker.000webhostapp.com/apps/wifi/?wifi=" + wifi.getWifiName();
        System.out.println(url);
    }

    public String getUrl()
    {
        return url;
    }

}
