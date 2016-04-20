package com.bitech.joke.module.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitech.joke.R;
import com.bitech.joke.annotation.ActivityInject;
import com.bitech.joke.base.BaseFragment;
import com.bitech.joke.bean.JokeBean;
import com.bitech.joke.callback.OnItemClickListener;
import com.bitech.joke.http.manager.RetrofitManager;
import com.bitech.joke.module.main.activity.HomeActivity;
import com.bitech.joke.module.main.activity.JokeDetailActivity;
import com.bitech.joke.module.main.adapter.HomeRecyclerAdapter;
import com.bitech.joke.module.main.presenter.HomePresener;
import com.bitech.joke.module.main.view.IHomeView;
import com.bitech.joke.utils.Constants;
import com.bitech.joke.utils.DividerItemDecoration;
import com.bitech.joke.utils.ToastUtil;
import com.bitech.joke.widgets.AutoLoadMoreRecyclerView;
import com.bitech.joke.widgets.AutoSwipeRefreshLayout;
import com.squareup.haha.perflib.Main;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * <p></p>
 * Created on 2016/4/19 14:19.
 *
 * @author Lucy
 */
@ActivityInject(contentViewId = R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements IHomeView, OnItemClickListener {


    private AutoSwipeRefreshLayout swipeRefreshLayout;

    private AutoLoadMoreRecyclerView recyclerView;

    private HomeRecyclerAdapter adapter;

    private int currentPage = 1;//当前页

    @Inject
    HomePresener homePresener;

    private int type = 1;

    @Override
    protected void initView(View contentView) {
        type = getArguments().getInt("type");//获取类型

        recyclerView = (AutoLoadMoreRecyclerView) contentView.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.color.material_blue_grey_50, 10));
        recyclerView.setOnLoadMoreListener(new AutoLoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                currentPage++;
                logger.i("-----加载更多数据-------");
                getData();
            }
        });


        swipeRefreshLayout = (AutoSwipeRefreshLayout) contentView.findViewById(R.id.home_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                getData();
            }
        });

        initInject();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.autoRefresh();
        getData();
    }

    //初始化依赖注解
    private void initInject() {
        getActivityComponent().inject(this);
     //   homePresener = new HomePresener(new RetrofitManager().getService());
        homePresener.attacthView(this);
    }

    //获取数据
    private void getData() {
        if (type == 1) {
            homePresener.getJokes(currentPage, Constants.PAGE_SIZE);
        } else if (type == 2) {
            homePresener.getImgJokes(currentPage, Constants.PAGE_SIZE);
        }
    }

    @Override
    public void updateRecyclerView(List<JokeBean.Joke> jokes) {
        if (currentPage == 1) {//更新
            adapter = new HomeRecyclerAdapter(getActivity(), jokes);
            if (type == 1) {
                adapter.setOnItemClickListener(this);
            }
            recyclerView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);//取消刷新
        } else {//加载更多
            if (jokes != null && jokes.size() > 0) {
                logger.i("--------获得更多的数据------");
                adapter.addMoreData(jokes);
                recyclerView.notifyMoreLoaded();//加载一个更多完毕
            } else {
                recyclerView.notifyAllLoaded();
                adapter.hideFooter();
                ToastUtil.showToast(getContext(), "加载完毕");
            }
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        //startActivity(new Intent(getActivity(),JokeDetailActivity.class));
    }
}
