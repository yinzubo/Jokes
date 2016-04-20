package com.bitech.joke.module.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bitech.joke.R;
import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.base.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

@ActivityInject(contentViewId = R.layout.activity_photo_view,isSlidr = true,
toolBarTitle = "图片详情")
public class PhotoViewActivity extends BaseActivity {

    @Bind(R.id.photo_view)
    PhotoView photoView;

    private String url;

    @Override
    protected void initView() {
        String url=getIntent().getStringExtra("url");
        if (url.endsWith(".gif")) {//gif图片
            Glide.with(this).load(url).asGif().crossFade()
                    .placeholder(R.drawable.ic_loading_small_bg).error(R.drawable.ic_fail).into(photoView);
        } else {
            Glide.with(this).load(url).crossFade().placeholder(R.drawable.ic_loading_small_bg)
                    .error(R.drawable.ic_fail).into(photoView);
        }

    }

}
