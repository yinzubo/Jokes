package com.bitech.joke.callback;

import com.bitech.joke.base.BaseView;
import com.bitech.joke.utils.Logger;
import com.bitech.joke.utils.Rxbus;

/**
 * <p></p>
 * Created on 2016/4/8 11:19.
 *
 * @author Lucy
 */
public class RequestCallback<T> implements IRequestCallback<T>{

    private static final Logger logger=Logger.getLogger();

    @Override
    public void beforeRequest() {
        logger.i("-------------before http request-----------------");
    }

    @Override
    public void requestError(String msg) {
        logger.i("-------------http request error-----------------");
        logger.i(msg);

        Rxbus.getInstance().post("request","网络连接失败");
    }

    @Override
    public void requestSucess(T data) {
        logger.i("-------------http request success-----------------");
        logger.i(data);
    }

    @Override
    public void requestComplete() {
        logger.i("-------------http request complete-----------------");

    }
}
