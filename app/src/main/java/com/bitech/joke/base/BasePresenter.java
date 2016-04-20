package com.bitech.joke.base;

/**
 * <p></p>
 * Created on 2016/4/6 16:27.
 *
 * @author Lucy
 */
public class BasePresenter<T extends BaseView, V> {

    protected T baseView;
    //关联View
    public  void attacthView(T baseView){
        this.baseView=baseView;
    }

}
