package com.bitech.joke.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthonycr.grant.PermissionsManager;
import com.bitech.joke.ActivityComponent;
import com.bitech.joke.ActivityModule;
import com.bitech.joke.DaggerActivityComponent;
import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.app.App;
import com.bitech.joke.utils.Logger;
import com.squareup.leakcanary.RefWatcher;

/**
 * <p></p>
 * Created on 2016/4/11 13:33.
 *
 * @author Lucy
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected static Logger logger=Logger.getLogger();

    protected int contentViewId;
    protected View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(getClass().isAnnotationPresent(ActivityInject.class)){
            ActivityInject annotation=getClass().getAnnotation(ActivityInject.class);
            contentViewId=annotation.contentViewId();
        }else{
            throw  new RuntimeException("Class must add annotations of ActivityInject.class");
        }
        contentView=inflater.inflate(contentViewId,null);

        initView(contentView);
        return contentView;
    }

    protected abstract  void initView(View contentView);


    public BaseFragment(){
        //监控
/*        RefWatcher refWatcher= App.getRefWatcher();
        refWatcher.watch(this);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }


    //使用Dagger2的设置
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(App.getAppComponent())
                .build();
    }
}
