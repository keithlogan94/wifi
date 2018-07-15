package com.jkeiffertechnologies.athomereminders;

import android.app.Service;
import android.os.Handler;

import java.io.IOException;

/**
 * Created by kbecker on 7/14/2018.
 */

abstract public class RepetitiveAction {

    private int secondsInterval = 5;

    public RepetitiveAction()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                try {
                    interval();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, secondsInterval * 1000);
            }
        }, secondsInterval * 1000);
    }

    abstract protected void interval() throws IOException;

}
