package com.bitech.joke;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * Created on 2016/4/7 14:15.
 *
 * @author Lucy
 */
@Module
public class ActivityModule {

    private final Activity activity;
    private final Fragment fragment;

    public ActivityModule(Activity activity){
        this.activity=activity;
        fragment=null;
    }

    public ActivityModule(Fragment fragment){
        this.fragment=fragment;
        activity=null;
    }
    @Provides
    @ActivityScope
    Activity providerActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    Fragment providerFragment(){
        return fragment;
    }
}
