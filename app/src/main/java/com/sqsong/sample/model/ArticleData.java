package com.sqsong.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 青松 on 2016/12/20.
 */

public class ArticleData implements Parcelable {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public ArticleData() {
    }

    protected ArticleData(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleData> CREATOR = new Creator<ArticleData>() {
        @Override
        public ArticleData createFromParcel(Parcel in) {
            return new ArticleData(in);
        }

        @Override
        public ArticleData[] newArray(int size) {
            return new ArticleData[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
