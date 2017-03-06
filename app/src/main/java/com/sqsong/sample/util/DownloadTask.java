package com.sqsong.sample.util;

import android.content.Context;
import android.os.AsyncTask;

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

public class DownloadTask extends AsyncTask<String, Long, Boolean> {

    public interface DownloadListener {
        void downloadPrepare(long fileLength);
        void downloadProgress(long percent, long totalLength);
        void downloadFinish(boolean success);
    }

    private int totalLength;
    private Context context;
    private DownloadListener listener;

    public DownloadTask(Context context, DownloadListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(String... downloadUrl) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(downloadUrl[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            totalLength = conn.getContentLength();
            if (listener != null) {
                listener.downloadPrepare(totalLength);
            }
            inputStream = new BufferedInputStream(conn.getInputStream(), 1024 * 8);
            String fileName = downloadUrl[0].substring(downloadUrl[0].lastIndexOf("/") + 1).replace("+", "");
            File file = new File(Util.getStoreDir(context));
            if (!file.exists()) {
                file.mkdir();
            }
            outputStream = new FileOutputStream(new File(file, fileName));

            byte[] data = new byte[1024];
            int count;
            long total = 0;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                publishProgress(total);

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
    protected void onProgressUpdate(Long... values) {
        if (listener != null) {
            int percent = (int)(values[0] * 100 / totalLength);
            listener.downloadProgress(percent, values[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (listener != null) {
            listener.downloadFinish(b);
        }
    }

}
