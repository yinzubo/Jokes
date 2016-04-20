package com.bitech.joke.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bitech.joke.R;
import com.bitech.joke.callback.OnItemClickListener;
import com.bitech.joke.utils.Logger;

import java.util.List;

/**
 * <p>RecyclerView.Adapter的封装
 * 默认不显示页脚
 * 默认线性管理
 * </p>
 * Created on 2016/4/11 15:06.
 *
 * @author Lucy
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    public static final Logger logger = Logger.getLogger();

    protected Context context;
    protected List<T> datas;//数据
    protected RecyclerView.LayoutManager layoutManager;
    protected boolean useAnimation;//是否使用动画

    protected boolean showFooter = true;//是否显示页脚

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_FOOTER = 3;

    protected OnItemClickListener onItemClickListener;

    protected BaseRecyclerAdapter(Context context, List<T> datas) {
        this(context, datas, true);
    }

    protected BaseRecyclerAdapter(Context context, List<T> datas, boolean useAnimation) {
        this(context, datas, useAnimation, new LinearLayoutManager(context));
    }

    protected BaseRecyclerAdapter(Context context, List<T> datas, boolean useAnimation, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.datas = datas;
        this.useAnimation = useAnimation;
        this.layoutManager = layoutManager;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new BaseRecyclerViewHolder(context,
                    LayoutInflater.from(context).inflate(R.layout.recycler_item_footer, parent, false));
        } else {
            return new BaseRecyclerViewHolder(context,
                    LayoutInflater.from(context).inflate(getItemLayoutId(), parent, false));

        }

    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (layoutManager != null) {
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    if (((StaggeredGridLayoutManager) layoutManager).getSpanCount() != 1) {
                        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                        layoutParams.setFullSpan(true);//item使用整个宽度或高度
                    }
                    //网格布局
                } else if (layoutManager instanceof GridLayoutManager) {
                    if (((GridLayoutManager) layoutManager)
                            .getSpanCount() != 1 && ((GridLayoutManager) layoutManager)
                            .getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                        throw new RuntimeException("网格布局列数大于1时应该继承SpanSizeLookup时处理底部加载时布局占满一行。");
                    }
                }
            }
        } else {
            bindData(holder, position, datas.get(position));
            if (useAnimation) {
                setAnimation(holder.itemView, position);
            }
            //点击
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClickListener(v, position);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        int i = showFooter ? 1 : 0;
        return datas != null ? datas.size() + i : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (showFooter && getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        }
        return bindViewType(position);
    }

    protected int bindViewType(int position) {
        return 0;
    }

    private int lastPosition = -1;

    //设置动画
    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            view.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //当动画运行之后就清除动画
        if (useAnimation && holder.itemView.getAnimation() != null && holder.itemView.getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    public void add(int position, T item) {
        datas.add(position, item);
        notifyItemInserted(position);//使用动画效果时需要使用这种方法
    }

    public void delete(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void addMoreData(List<T> datas) {
        int start = this.datas.size();
        this.datas.addAll(datas);
        notifyItemRangeChanged(start, datas.size());
    }

    public List<T> getDatas() {
        return this.datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    //显示
    public void showFooter() {
        notifyItemInserted(getItemCount());
        showFooter = true;
    }

    //隐藏
    public void hideFooter() {
        notifyItemRemoved(getItemCount() - 1);
        showFooter = false;
    }

    //点击监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //返回LayoutView
    protected abstract int getItemLayoutId();

    //binding数据
    protected abstract void bindData(BaseRecyclerViewHolder holder, int position, T item);
}
