package com.bitech.joke.module.main.model;

import com.bitech.joke.bean.JokeBean;
import com.bitech.joke.callback.IRequestCallback;
import com.bitech.joke.http.service.Service;
import com.bitech.joke.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <p></p>
 * Created on 2016/4/19 14:48.
 *
 * @author Lucy
 */
public class JokeModel {

    private Service apiService;

    @Inject
    public JokeModel(Service apiService) {

        this.apiService = apiService;
    }

    //获取玩笑列表
    public void getJokes(int page, int pageSize, final IRequestCallback callback) {

        List<JokeBean.Joke> jokes = new ArrayList<>();

        apiService.getJokes(page, pageSize, Constants.APP_KEY).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                callback.beforeRequest();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JokeBean>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getMessage());
                    }

                    @Override
                    public void onNext(JokeBean jokeBean) {
                        callback.requestSucess(jokeBean);
                    }
                });

    }

    //获取图片玩笑列表
    public void getImgJokes(int page, int pageSize, final IRequestCallback callback) {

        List<JokeBean.Joke> jokes = new ArrayList<>();

        apiService.getImgJokes(page, pageSize, Constants.APP_KEY).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                callback.beforeRequest();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JokeBean>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getMessage());
                    }

                    @Override
                    public void onNext(JokeBean jokeBean) {
                        callback.requestSucess(jokeBean);
                    }
                });

    }
}
