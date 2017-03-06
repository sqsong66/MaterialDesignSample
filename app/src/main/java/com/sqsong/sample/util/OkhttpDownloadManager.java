package com.sqsong.sample.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by 青松 on 2017/2/24.
 */

public class OkhttpDownloadManager {

    private static final int DOWNLOAD_PREPARING = 0;
    private static final int DOWNLOAD_DOWNLOADING = 1;
    private static final int DOWNLOAD_SUCCESS = 2;

    private Call mCall;
    private String mUrl;
    private File mFile;
    private long startPosition;
    private OkHttpClient mClient;
    private DownloadTask.DownloadListener mListener;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (mListener == null) return;
            switch (msg.what) {
                case DOWNLOAD_PREPARING:
                    Long totalLenght = (Long) msg.obj;
                    mListener.downloadPrepare(totalLenght);
                    break;
                case DOWNLOAD_DOWNLOADING:
                    Pair<Long, Long> pair = (Pair<Long, Long>) msg.obj;
                    mListener.downloadProgress(pair.first, pair.second);
                    break;
                case DOWNLOAD_SUCCESS:
                    mListener.downloadFinish(true);
                    break;
            }
        }
    };

    public OkhttpDownloadManager(Context context, String url, DownloadTask.DownloadListener listener) {
        this.mUrl = url;
        this.mFile = buildFile(context, url);
        this.mListener = listener;
        this.mClient = buildHttpClient();
    }

    private File buildFile(Context context, String url) {
        if (TextUtils.isEmpty(url))
            return null;
        String fileName = url.substring(url.lastIndexOf("/") + 1).replace("+", "");
        return new File(Util.getStoreDir(context), fileName);
    }

    private OkHttpClient buildHttpClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response.newBuilder().body(new ProgressResponseBody(response.body(), mHandler)).build();
            }
        };
        return new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
    }

    private Call makeCall(long startPosition) {
        this.startPosition = startPosition;
        Request request = new Request.Builder()
                .url(mUrl)
                .header("RANGE", "bytes=" + startPosition + "-")
                .build();
        return mClient.newCall(request);
    }

    public void downloadFile(final long startPosition) {
        mCall = makeCall(startPosition);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveFile(response, startPosition);
            }
        });
    }

    private void saveFile(Response response, long startPosition) {
        if (response == null) return;
        ResponseBody responseBody = response.body();
        InputStream inputStream = responseBody.byteStream();
        FileChannel fileChannel = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(mFile, "rwd");
            fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,
                    startPosition, responseBody.contentLength());
            byte[] buffer = new byte[2048];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                mappedBuffer.wrap(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    class ProgressResponseBody extends ResponseBody {

        private Handler mHandler;
        private ResponseBody mResponseBody;
        private BufferedSource mBufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, Handler handler) {
            this.mResponseBody = responseBody;
            this.mHandler = handler;

            Message message = mHandler.obtainMessage();
            message.what = DOWNLOAD_PREPARING;
            message.obj = responseBody.contentLength();
            mHandler.sendMessage(message);
        }

        @Override
        public MediaType contentType() {
            return mResponseBody.contentType();
        }

        @Override
        public long contentLength() {
            return mResponseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (mBufferedSource == null) {
                mBufferedSource = Okio.buffer(source(mResponseBody.source()));
            }
            return mBufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long curReadBytes = 0;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long readBytes = super.read(sink, byteCount);
                    long read = readBytes == -1 ? 0 : readBytes;
                    curReadBytes += read;

                    Message message = mHandler.obtainMessage();
                    message.what = DOWNLOAD_DOWNLOADING;
                    Pair<Long, Long> pair = new Pair<>(curReadBytes, mResponseBody.contentLength());
                    message.obj = pair;
                    mHandler.sendMessage(message);
                    if (readBytes == -1) {
                        Message msg = mHandler.obtainMessage();
                        message.what = DOWNLOAD_SUCCESS;
                        mHandler.sendMessage(msg);
                    }
                    return readBytes;
                }
            };
        }
    }
}
