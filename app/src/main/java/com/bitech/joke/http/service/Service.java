package com.bitech.joke.http.service;


import com.bitech.joke.bean.JokeBean;
import com.bitech.joke.bean.JokeBean.Joke;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>使用retrofit</p>
 * Created on 2016/4/6 14:48.
 *
 * @author Lucy
 */
public interface Service {

    @GET("/joke/content/text.from")
    Observable<JokeBean> getJokes(@Query("page")int page, @Query("pagesize")int pageSize, @Query("key")String key);

    @GET("/joke/img/text.from")
    Observable<JokeBean> getImgJokes(@Query("page")int page, @Query("pagesize")int pageSize, @Query("key")String key);

}
