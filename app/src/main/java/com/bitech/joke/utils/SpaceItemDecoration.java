package com.bitech.joke.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * <p>recyclerview的分割线
 * 利用的是背景与item之间的间隔完成
 * </p>
 * Created on 2016/4/12 13:53.
 *
 * @author Lucy
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);//获取view所在的位置
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = 0;

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
            if (position < ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount()) {
               //利用Item的margin配合RecyclerView的margin值使间隔相等，这里只需设置第一行相对于顶部有高度
                outRect.top = space;
            }
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            if (position < ((GridLayoutManager) parent.getLayoutManager()).getSpanCount()) {
               //保证第一行有相对顶部有高度
                outRect.top = space;
            }
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (position == 0) {
                outRect.top = space;
            }
        }
    }
}
