package com.sqsong.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 青松 on 2016/12/20.
 */

public class MusicData implements Parcelable {

    private String musicImage;
    private String musicTitle;
    private String musicAuthor;

    public MusicData(String musicImage, String musicTitle, String musicAuthor) {
        this.musicImage = musicImage;
        this.musicTitle = musicTitle;
        this.musicAuthor = musicAuthor;
    }

    protected MusicData(Parcel in) {
        musicImage = in.readString();
        musicTitle = in.readString();
        musicAuthor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicImage);
        dest.writeString(musicTitle);
        dest.writeString(musicAuthor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicData> CREATOR = new Creator<MusicData>() {
        @Override
        public MusicData createFromParcel(Parcel in) {
            return new MusicData(in);
        }

        @Override
        public MusicData[] newArray(int size) {
            return new MusicData[size];
        }
    };

    public String getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(String musicImage) {
        this.musicImage = musicImage;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicAuthor() {
        return musicAuthor;
    }

    public void setMusicAuthor(String musicAuthor) {
        this.musicAuthor = musicAuthor;
    }
}
