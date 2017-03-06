package com.sqsong.sample.video;

import android.view.SurfaceHolder;

import com.sqsong.sample.base.BasePresenter;
import com.sqsong.sample.base.BaseView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by 青松 on 2017/3/3.
 */

public interface VideoContract {

    interface View extends BaseView<Presenter> {

        void onVideoSizeChanged(int width, int height);

        void onPlayCompeted(IMediaPlayer mediaPlayer);
    }

    interface Presenter extends BasePresenter {

        void initMusicPlayer();

        void setAudioPath(String path);

        void startPlay();

        void stopPlay();

        void setPlayerSurfaceHolder(SurfaceHolder holder);

        void release();

        long getDuration();

        long getCurrentPos();

        void seekTo(long position);

        boolean isPlaying();
    }

}
