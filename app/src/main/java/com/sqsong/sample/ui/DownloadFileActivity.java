package com.sqsong.sample.ui;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sqsong.sample.R;
import com.sqsong.sample.util.DownloadTask;
import com.sqsong.sample.util.OkhttpDownloadManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;

public class DownloadFileActivity extends BaseActivity implements View.OnClickListener {

    public static final String DOWNLOAD_URL = "http://downapp.moxian.com/moxian/Moxian+_V2.0_20161102015217.apk";

    @BindView(R.id.progress_tv)
    TextView mProgressTv;

    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    @BindView(R.id.download_tv)
    TextView mDownloadTv;

    @BindView(R.id.percent_tv)
    TextView mPercentTv;

    @BindView(R.id.okhttp_progressbar)
    ProgressBar mOkhttpBar;

    @BindView(R.id.start_btn)
    TextView mStartTv;

    @BindView(R.id.stop_btn)
    TextView mStopTv;

    private long startPos = 0;
    private boolean isDownloading;
    private OkhttpDownloadManager mDownloadManager;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(3);

    @Override
    protected int getResourceId() {
        return R.layout.activity_download_file;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        mDownloadManager = new OkhttpDownloadManager(getApplicationContext(), DOWNLOAD_URL, mOkhttpDownloadListener);

        mProgressBar.setProgress(0);
        mProgressTv.setText(String.format(getString(R.string.progress_text), 0));
        mPercentTv.setText(String.format(getString(R.string.progress_text), 0));
        mDownloadTv.setOnClickListener(this);
        mStartTv.setOnClickListener(this);
        mStopTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_tv:
                startDownloadFile();
                break;
            case R.id.start_btn:
                mDownloadManager.downloadFile(startPos);
                break;
            case R.id.stop_btn:
                mDownloadManager.pause();
                break;
        }
    }

    private DownloadTask.DownloadListener mOkhttpDownloadListener = new DownloadTask.DownloadListener() {
        @Override
        public void downloadPrepare(long fileLength) {
            mOkhttpBar.setProgress(0);
            mPercentTv.setText("preparing...");
        }

        @Override
        public void downloadProgress(long readBytes, long totalLength) {
            startPos = readBytes;

            int percent = (int) ((startPos + readBytes) * 100 / totalLength);
            mOkhttpBar.setProgress(percent);
            Log.i("sqsong", "Current Thread: " + Thread.currentThread().getName());
            mPercentTv.setText(String.format(getString(R.string.progress_text), percent));
        }

        @Override
        public void downloadFinish(boolean success) {
            Toast.makeText(DownloadFileActivity.this, "Download Success!!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void startDownloadFile() {
        if (isDownloading) {
            return;
        }

        DownloadTask downloadTask = new DownloadTask(getApplicationContext(),
            new DownloadTask.DownloadListener() {
                @Override
                public void downloadPrepare(long totalLength) {
                    isDownloading = true;
                    mProgressBar.setProgress(0);
                    mProgressTv.setText("preparing...");
                }

                @Override
                public void downloadProgress(long progress, long totalLength) {
                    mProgressBar.setProgress((int) progress);
                    mProgressTv.setText(String.format(getString(R.string.progress_text), progress));
                }

                @Override
                public void downloadFinish(boolean success) {
                    isDownloading = false;
                    Toast.makeText(DownloadFileActivity.this, "Download Success!!!", Toast.LENGTH_SHORT).show();
                }
            });

        downloadTask.executeOnExecutor(mExecutor, DOWNLOAD_URL);
    }
}
