package com.fly.anrmonitorlib;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fj on 2018/1/25.
 */
public class LogMonitor {
    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mLogThread = new HandlerThread("log_thread");
    private static ArrayList<String> stackList = new ArrayList<>();
    private Handler mIoHandler;
    /**1000内执行完消息则认为不卡顿*/
    private static final long TIME_BLOCK = 1000L;
    private static final int AWAIT = 100;
    private long preTime = 0l;
    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            stackList.add(sb.toString());
            Log.e(AnrMonitorUtil.TAG, sb.toString());
            SystemClock.sleep(AWAIT);
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }


    public void startMonitor() {
        preTime = System.currentTimeMillis();
        mIoHandler.post(mLogRunnable);
    }

    public void removeMonitor() {
        if (System.currentTimeMillis() - preTime >TIME_BLOCK){
            //保存卡顿日志到本地

        }else{
            stackList.clear();
        }
    }

    public void close(){
        stackList.clear();
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
