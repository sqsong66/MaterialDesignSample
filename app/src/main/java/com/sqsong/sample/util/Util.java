package com.sqsong.sample.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 青松 on 2016/11/24.
 */

public class Util {

    /**
     * Check the network status.
     * @param context
     * @return network status.
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static String getNormalFormatTime(String t) {
        // 2016-12-21T03:39:20Z
        String time = "None";
        if (TextUtils.isEmpty(t)) {
            return time;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(t);
            format.applyPattern("yyyy-MM-dd"); // MM/dd/yyyy HH:mm aaa
            time = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date getPublishDate(String t) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            date = format.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String getStoreDir(Context context) {
        String childPath = "/download";
        String externalDir = context.getExternalCacheDir().getPath() + childPath;
        String internalDir = context.getCacheDir().getPath() + childPath;
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? externalDir : internalDir;
    }

}
