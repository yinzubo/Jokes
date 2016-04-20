package com.bitech.joke.module.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bitech.joke.R;
import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.base.BaseActivity;
import com.bitech.joke.module.main.Fragment.HomeFragment;
import com.bitech.joke.module.main.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityInject(contentViewId = R.layout.activity_home, isSlidr = false,
        toolBarIndicator = 0,toolBarTitle ="笑话联盟" )
public class HomeActivity extends BaseActivity {

    public final int WORD=1;
    public final int IMG=2;

    @Bind(R.id.home_tab_word)
    TextView wordTextView;

    @Bind(R.id.home_tab_img)
    TextView imgTextView;

    @Bind(R.id.home_viewpager)
     ViewPager viewPager;

    private FragmentViewPagerAdapter adapter;

    @Override
    protected void initView() {


        List<Fragment> fragmentList=new ArrayList<>();
        Fragment wordFragment=new HomeFragment();
        Bundle wordBundle=new Bundle();
        wordBundle.putInt("type",WORD);
        wordFragment.setArguments(wordBundle);
        fragmentList.add(wordFragment);

        Fragment imgFragment=new HomeFragment();
        Bundle imgBundle=new Bundle();
        imgBundle.putInt("type",IMG);
        imgFragment.setArguments(imgBundle);
        fragmentList.add(imgFragment);

        adapter=new FragmentViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);

    }
    //点击
    public void  tab(View v){
        switch (v.getId()){
            case R.id.home_tab_word:
/*                wordTextView.setTextColor(getResources().getColor(R.color.material_white));
                imgTextView.setTextColor(getColor(R.color.material_black));*/
                viewPager.setCurrentItem(0);
                break;
            case R.id.home_tab_img:
/*                imgTextView.setTextColor(getColor(R.color.material_white));
                wordTextView.setTextColor(getColor(R.color.material_black));*/
                viewPager.setCurrentItem(1);
                break;
        }
    }

}
