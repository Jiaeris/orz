package com.every.md;

import android.app.Application;
import android.util.Log;

/**
 * Created by Yunga on 2017/9/1.
 */

public class MDApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "[MDApplication]";

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e(TAG, "uncaughtException: ", throwable);
    }
}
