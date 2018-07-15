package com.jkeiffertechnologies.athomereminders;


import java.io.IOException;
import android.content.Context;

/**
 * Created by kbecker on 7/14/2018.
 */

public class WebsiteActiveWifiUpdater extends RepetitiveAction {

    Context context = null;

    public WebsiteActiveWifiUpdater(Context _context)
    {
        super();
        context = _context;
    }

    private void updateServer() throws IOException {
        GetRequest getRequest = new GetRequest(new ServerWebAddress(context));
        System.out.println(getRequest.start());
    }

    @Override
    protected void interval() throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    updateServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }
}
