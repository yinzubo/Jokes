package com.bitech.joke.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bitech.joke.bean.JokeBean;

import java.util.List;

/**
 * <p></p>
 * Created on 2016/4/19 15:15.
 *
 * @author Lucy
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public FragmentViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList){
        super(manager);
        this.fragmentList=fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList!=null?fragmentList.size():0;
    }


}
