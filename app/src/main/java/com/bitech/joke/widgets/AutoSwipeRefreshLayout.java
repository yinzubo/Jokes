package com.bitech.joke.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p></p>
 * Created on 2016/4/20 17:26.
 *
 * @author Lucy
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout{

    public AutoSwipeRefreshLayout(Context context) {
        super(context);
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void autoRefresh(){
        try {

            Field mCircleView=SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress=(View)mCircleView.get(this);
            progress.setVisibility(View.VISIBLE);

            Method setRefresh=SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class);
            setRefresh.setAccessible(true);
            setRefresh.invoke(this,true,true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
