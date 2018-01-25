package com.fly.anrmonitorlib;

import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * 参考文档:
 * 1、https://mp.weixin.qq.com/s/MthGj4AwFPL2JrZ0x1i4fw 广研Android卡顿监控
 * 2、
 * Created by Fj on 2018/1/25.
 */
public class AnrMonitorUtil {
    public static final String TAG = "LoopAnrMonitor";

    public static void start() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";
            @Override
            public void println(String x) {
                Log.i(TAG, "println: "+x);
                if (x.startsWith(START)) {
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }
            }
        });
    }

}
