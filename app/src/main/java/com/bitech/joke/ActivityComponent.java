package com.bitech.joke;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.bitech.joke.module.main.Fragment.HomeFragment;
import com.bitech.joke.module.main.activity.HomeActivity;

import dagger.Component;

/**
 * <p></p>
 * Created on 2016/4/7 14:14.
 *
 * @author Lucy
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    Fragment fragment();

    void inject(HomeActivity homeActivity);

    void inject(HomeFragment homeFragment);
}
