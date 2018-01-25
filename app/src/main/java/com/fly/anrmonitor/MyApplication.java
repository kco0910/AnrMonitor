package com.fly.anrmonitor;

import android.app.Application;

import com.fly.anrmonitorlib.AnrMonitorUtil;

/**
 * Created by Fj on 2018/1/25.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AnrMonitorUtil.start();
    }
}
