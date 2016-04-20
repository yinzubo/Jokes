package com.bitech.joke.app;

import android.app.Application;
import android.content.Context;

import com.bitech.joke.AppComponent;
import com.bitech.joke.AppModule;
import com.bitech.joke.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * <p></p>
 * Created on 2016/4/5 14:06.
 *
 * @author Lucy
 */
public class App extends Application {

    private static App mInstance;//App的实例
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        //内存泄漏监测
       // refWatcher= LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(){
        return mInstance.refWatcher;
    }
    public static Context getContext() {
        return mInstance;
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(mInstance)).build();
    }
}
