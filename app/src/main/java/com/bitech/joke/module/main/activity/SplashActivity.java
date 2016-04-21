package com.bitech.joke.module.main.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bitech.joke.R;
import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.base.BaseActivity;
import com.bitech.joke.utils.PermissionHelper;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import javax.inject.Inject;

import butterknife.Bind;

@ActivityInject(contentViewId = R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_layout)
    RelativeLayout relativeLayout;

    private PermissionHelper helper;

    @Override
    protected void initView() {

        helper = new PermissionHelper(this);
        helper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                runApp();
            }
        });

        if (Build.VERSION.SDK_INT < 23) {//23以后的权限需要申请
            runApp();
        } else {
            if (helper.isAllRequestedPermissionGranted()) {//已经全部授权
                runApp();
            } else {
                helper.applyPermissions();
            }
        }

    }

    private void runApp() {
        AdManager.getInstance(this).init("7ce0c2e106177a9c","841a32046abd1266");//正式
       // AdManager.getInstance(this).init("85aa56a59eac8b3d", "a14006f66f58d5d7");//测试
        setSplashAd();
    }
    //设置开屏广告
    private void setSplashAd(){
        SplashView splashView=new SplashView(this,null);
        splashView.setIsJumpTargetWhenFail(true);//展示失败后直接跳转
        splashView.setShowReciprocal(true);//设置显示倒计时
        splashView.hideCloseBtn(false);//隐藏关闭按钮

        Intent intent=new Intent(this,HomeActivity.class);
        splashView.setIntent(intent);

        View splash=splashView.getSplashView();

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(-1,-1);
        layoutParams.addRule(RelativeLayout.ABOVE,R.id.view_divider);
        relativeLayout.addView(splash,layoutParams);

        SpotManager.getInstance(this).showSplashSpotAds(this, splashView, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim_splash_enter));
            }

            @Override
            public void onShowFailed() {

            }

            @Override
            public void onSpotClosed() {

            }

            @Override
            public void onSpotClick(boolean b) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        helper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helper.onActivityResult(requestCode,resultCode,data);
    }

}
