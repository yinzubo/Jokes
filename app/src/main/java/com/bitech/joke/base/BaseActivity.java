package com.bitech.joke.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.bitech.joke.ActivityComponent;
import com.bitech.joke.ActivityModule;
import com.bitech.joke.BuildConfig;
import com.bitech.joke.DaggerActivityComponent;
import com.bitech.joke.R;

import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.app.App;
import com.bitech.joke.app.AppManager;
import com.bitech.joke.utils.Logger;
import com.bitech.joke.utils.Rxbus;
import com.bitech.joke.utils.slidr.SlidrUtil;
import com.bitech.joke.utils.slidr.model.SlidrInterface;

import org.w3c.dom.Text;

import butterknife.ButterKnife;

/**
 * <p></p>
 * Created on 2016/4/5 13:47.
 *
 * @author Lucy
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public static final Logger logger = Logger.getLogger();

    private int contentViewId;
    private boolean isSlidr;//是否开启滑动关闭

    protected Toolbar toolbar;
    private TextView titleTextView;
    private String toolBarTitle;
    private int toolBarIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
 /*           App.getRefWatcher().watch(this);//监测Activity运行时是否发生内存泄漏

  */
        if (getClass().isAnnotationPresent(ActivityInject.class)) {
            ActivityInject annotation = getClass().getAnnotation(ActivityInject.class);
            contentViewId = annotation.contentViewId();
            isSlidr = annotation.isSlidr();
            toolBarTitle = annotation.toolBarTitle();
            toolBarIndicator = annotation.toolBarIndicator();

        } else {
            throw new RuntimeException("there is a runtime exception,because activity has to annotation @ActivityInject");
        }

        //是否开启苛责模式
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        logger.i("设置contentViewId:" + contentViewId);
        setContentView(contentViewId);
        ButterKnife.bind(this);

        //初始化toolbar
        initToolbar();
        if (isSlidr) {
           SlidrUtil.initSlidrDefaultConfig(this, true);
        }
        initView();
    }

    //初始化toolbar
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        titleTextView = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");//指定toolbar的title不起作用
            titleTextView.setText(toolBarTitle);
            if (toolBarIndicator != 0) {//等于0时toolbar左边不设置图片
                if (toolBarIndicator != -1) {
                    getSupportActionBar().setHomeAsUpIndicator(toolBarIndicator);
                } else {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_back);
                    //点击后退退出
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }else{
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);//不显示左边的指示图标
            }
        }
    }

    protected void setToolBarTitle(String toolBarTitle) {
        if (getSupportActionBar() != null) {
            titleTextView.setText(toolBarTitle);
        }
    }

    protected void setToolBarTitle(int resId) {
        if (getSupportActionBar() != null) {
            titleTextView.setText(toolBarTitle);
        }
    }

    protected void setToolBarIndicator(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }

    protected ActionBar getToolbar() {
        return getSupportActionBar();
    }

    protected abstract void initView();

    protected void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivity(String className) {
        startActivity(className, null);
    }

    protected void startActivity(String className, Bundle bundle) {
        Intent intent = new Intent(className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用PermissionManager对权限的申请管理
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    //使用Dagger2的设置
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getAppComponent())
                .build();
    }
}
