package com.bitech.joke.callback;

/**
 * <p>请求的回调</p>
 * Created on 2016/4/6 16:18.
 *
 * @author Lucy
 */
public interface IRequestCallback<T> {

    void beforeRequest();

    void requestError(String msg);

    void requestComplete();

    void requestSucess(T data);
}
