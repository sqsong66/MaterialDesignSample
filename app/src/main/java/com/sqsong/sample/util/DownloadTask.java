package com.sqsong.sample.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 青松 on 2017/1/3.
 */

public class DownloadTask extends AsyncTask<String, Integer, Boolean> {

    public interface DownloadListener {
        void downloadPrepare();
        void downloadProgress(int progress);
        void downloadFinish(boolean success);
    }

    private Context context;
    private DownloadListener listener;

    public DownloadTask(Context context, DownloadListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.downloadPrepare();
        }
    }

    @Override
    protected Boolean doInBackground(String... downloadUrl) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(downloadUrl[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            int totalLength = conn.getContentLength();
            inputStream = new BufferedInputStream(conn.getInputStream(), 1024 * 8);
            String fileName = downloadUrl[0].substring(downloadUrl[0].lastIndexOf("/") + 1).replace("+", "");
            File file = new File(getStoreDir(context));
            if (!file.exists()) {
                file.mkdir();
            }
            outputStream = new FileOutputStream(new File(file, fileName));

            byte[] data = new byte[1024];
            int count;
            long total = 0;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                publishProgress((int)total * 100 / totalLength);

                outputStream.write(data, 0, count);
                outputStream.flush();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (listener != null) {
            listener.downloadProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (listener != null) {
            listener.downloadFinish(b);
        }
    }

    public String getStoreDir(Context context) {
        String childPath = "/download";
        String externalDir = context.getExternalCacheDir().getPath() + childPath;
        String internalDir = context.getCacheDir().getPath() + childPath;
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? externalDir : internalDir;
    }

}
