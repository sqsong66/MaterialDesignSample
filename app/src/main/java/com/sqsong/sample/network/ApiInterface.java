package com.sqsong.sample.network;

import com.sqsong.sample.model.ArticleBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 青松 on 2016/11/24.
 */

public interface ApiInterface {

    String BASE_URL = "https://newsapi.org/v1/";

    @GET("articles")
    Observable<ArticleBean> getArticles(@Query("source") String source, @Query("apiKey") String apiKey, @Query("sortBy") String sortBy);

    /*@GET("Android/10/{index}")
    Observable<GankBean> getAndroidData(@Path("index") int pageIndex);

    @GET("iOS/10/{index}")
    Observable<GankBean> getIOSData(@Path("index") int pageIndex);

    @GET("前端/10/{index}")
    Observable<GankBean> getWebData(@Path("index") int pageIndex);*/

}
