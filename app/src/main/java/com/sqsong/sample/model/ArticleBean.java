package com.sqsong.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 青松 on 2016/12/20.
 */

public class ArticleBean implements Parcelable {

    private String status;
    private String source;
    private String sortBy;
    private List<ArticleData> articles;

    public ArticleBean() {
    }

    protected ArticleBean(Parcel in) {
        status = in.readString();
        source = in.readString();
        sortBy = in.readString();
        articles = in.createTypedArrayList(ArticleData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(source);
        dest.writeString(sortBy);
        dest.writeTypedList(articles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel in) {
            return new ArticleBean(in);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<ArticleData> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleData> articles) {
        this.articles = articles;
    }
}
