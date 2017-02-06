package com.sqsong.sample.ui;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sqsong.sample.R;
import com.sqsong.sample.util.DownloadTask;

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

    private boolean isDownloading;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(3);

    @Override
    protected int getResourceId() {
        return R.layout.activity_download_file;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        mProgressBar.setProgress(0);
        mProgressTv.setText(String.format(getString(R.string.progress_text), 0));
        mDownloadTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_tv:
                startDownloadFile();
                break;
        }
    }

    private void startDownloadFile() {
        if (isDownloading) {
            return;
        }

        DownloadTask downloadTask = new DownloadTask(getApplicationContext(),
            new DownloadTask.DownloadListener() {
                @Override
                public void downloadPrepare() {
                    isDownloading = true;
                    mProgressBar.setProgress(0);
                    mProgressTv.setText("preparing...");
                }

                @Override
                public void downloadProgress(int progress) {
                    mProgressBar.setProgress(progress);
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
