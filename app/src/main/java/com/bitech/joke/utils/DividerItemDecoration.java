package com.bitech.joke.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * <p>RecyclerView分割线
 * 系统属性divider设置分割线
 * dividerHeight设置分割线的高度
 * </p>
 * Created on 2016/4/12 15:24.
 *
 * @author Lucy
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.divider, android.R.attr.dividerHeight};

    private Drawable drawable;
    private int height;

    public DividerItemDecoration(Context context) {
        drawable=context.getResources().getDrawable(android.R.color.white);
        height=0;
    }
    public DividerItemDecoration(Context context,int color,int height) {
        drawable=context.getResources().getDrawable(color);
        this.height=height;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            drawHorizontal(c, parent);
        } else {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    //返回列数
    private int getSpanCount(RecyclerView parent) {
        int spanCount = 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    //水平分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin + height;
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + height;

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    //垂直分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + height;
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {//最后一列
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orentation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orentation == StaggeredGridLayoutManager.HORIZONTAL) {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {//最后一列
                    return true;
                }
            } else {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orentation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orentation == StaggeredGridLayoutManager.HORIZONTAL) {//水平
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {    //垂直
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount) {
                    return true;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (pos + 1 == childCount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (isLastColum(parent, position, spanCount, childCount)) {//最后一行
            outRect.set(0, 0, 0, height);
        } else if (isLastRow(parent, position, spanCount, childCount)) {//最后一列
            outRect.set(0, 0, height, 0);
        } else {
            outRect.set(0, 0, height, height);
        }

    }


}
