package com.bitech.joke;

import android.content.Context;

import com.bitech.joke.app.App;
import com.bitech.joke.http.manager.RetrofitManager;
import com.bitech.joke.http.service.Service;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * Created on 2016/4/7 14:12.
 *
 * @author Lucy
 */
@Module
public class AppModule {

    private final App app;

    public AppModule(App app){
        this.app=app;
    }

    @Provides
    @Singleton
    Context providerContext(){
        return app;
    }

    @Provides
    @Singleton
    Service providerApiService(RetrofitManager retrofitManager){
        return retrofitManager.getService();
    }

}
