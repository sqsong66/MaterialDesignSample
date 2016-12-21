package com.sqsong.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by 青松 on 2016/12/20.
 */
public class BaseApplication extends Application {

    public static String cacheDir;
    private static Context mGlobalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mGlobalContext = getAppContext();

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    public static Context getAppContext() {
        return mGlobalContext;
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

}
