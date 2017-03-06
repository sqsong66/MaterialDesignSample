package com.sqsong.sample.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sqsong.sample.R;
import com.sqsong.sample.ui.BaseActivity;
import com.sqsong.sample.util.DensityUtil;

import java.util.Formatter;
import java.util.Locale;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayerActivity extends BaseActivity implements VideoContract.View, View.OnClickListener {

    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001;
    private String mMusicPath = Environment.getExternalStorageDirectory() + "/Movies/Game.of.Thrones.mp4"; //Game.of.Thrones

    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceView;

    @BindView(R.id.starting_time_tv)
    TextView mCurTimeTv;

    @BindView(R.id.remaining_time_tv)
    TextView mTotalTimeTv;

    @BindView(R.id.seekbar)
    SeekBar mSeekbar;

    @BindView(R.id.control_tv)
    TextView mControlTv;

    private boolean mDragging;
    private Formatter mFormatter;
    private StringBuilder mFormatBuilder;

    private VideoContract.Presenter mPresenter;

    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mPresenter != null) {
                mPresenter.setPlayerSurfaceHolder(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mSeekChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) return;

            long duration = mPresenter.getDuration();
            long position = (duration * progress) / 1000L;
            mPresenter.seekTo(position);
            mCurTimeTv.setText(stringForTime((int) position));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mDragging = true;
            mSeekbar.removeCallbacks(mProgressRunnable);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mDragging = false;
            mSeekbar.post(mProgressRunnable);
        }
    };

    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            long pos = setSeekProgress();
            if (!mDragging && mPresenter.isPlaying()) {
                long delay = 1000 - (pos % 1000);
                Log.i("sqsong", "Delay Time: " + delay + " ms.");
                mSeekbar.postDelayed(mProgressRunnable, /*delay*/1000);
            }
        }
    };

    private long setSeekProgress() {
        if (mDragging) {
            return 0;
        }
        long curPos = mPresenter.getCurrentPos();
        long duration = mPresenter.getDuration();
        if (duration > 0) {
            long pos = 1000 * curPos / duration;
            mSeekbar.setProgress((int) pos);
        }
        mCurTimeTv.setText(stringForTime((int) curPos));
        mTotalTimeTv.setText(stringForTime((int) duration));
        return curPos;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_music_player;
    }

    @Override
    protected void initEvent() {
        setupToolbar();
        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mPresenter = new VideoPresenter(this);
        mPresenter.initMusicPlayer();

        mSeekbar.setMax(1000);
        mSeekbar.postDelayed(mProgressRunnable, 800);
        mControlTv.setOnClickListener(this);
        mSeekbar.setOnSeekBarChangeListener(mSeekChangeListener);

        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            mPresenter.setAudioPath(mMusicPath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.setAudioPath(mMusicPath);
            } else {
                Toast.makeText(this, "Please allow application read SDcard!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_tv:
                if (mPresenter.isPlaying()) {
                    mPresenter.stopPlay();
                    mControlTv.setText("Start");
                    mSeekbar.removeCallbacks(mProgressRunnable);
                } else {
                    mPresenter.startPlay();
                    mControlTv.setText("Pause");
                    mSeekbar.post(mProgressRunnable);
                }
                break;
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mSurfaceView.getLayoutParams();
        int curWidth = DensityUtil.getScreenWidth();
        int newHeight = curWidth * height / width;
        layoutParams.width = curWidth;
        layoutParams.height = newHeight;
        mSurfaceView.getHolder().setFixedSize(curWidth, newHeight);
        mSurfaceView.requestLayout();
    }

    @Override
    public void onPlayCompeted(IMediaPlayer mediaPlayer) {
        mCurTimeTv.setText(stringForTime(0));
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.release();
    }

}
