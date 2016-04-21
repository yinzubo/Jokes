package com.bitech.joke.module.main.presenter;

import com.bitech.joke.base.BasePresenter;
import com.bitech.joke.bean.JokeBean;
import com.bitech.joke.callback.RequestCallback;
import com.bitech.joke.http.service.Service;
import com.bitech.joke.module.main.model.JokeModel;
import com.bitech.joke.module.main.view.IHomeView;

import java.util.List;

import javax.inject.Inject;

/**
 * <p></p>
 * Created on 2016/4/19 14:44.
 *
 * @author Lucy
 */
public class HomePresener extends BasePresenter<IHomeView,JokeBean.Joke> {

    private JokeModel jokeModel;

    @Inject
    public HomePresener(Service apiService){
        jokeModel=new JokeModel(apiService);
    }

    //获取玩笑列表
    public void getJokes(int page,int pageSize){

        jokeModel.getJokes(page,pageSize,new RequestCallback(){
            @Override
            public void requestSucess(Object data) {
                super.requestSucess(data);

                List<JokeBean.Joke> jokes=((JokeBean)data).data;
                baseView.updateRecyclerView(jokes);
            }

        });
    }

    //获取图片玩笑列表
    public void getImgJokes(int page,int pageSize){

        jokeModel.getImgJokes(page,pageSize,new RequestCallback(){
            @Override
            public void requestSucess(Object data) {
                super.requestSucess(data);

                List<JokeBean.Joke> jokes=((JokeBean)data).data;
                baseView.updateRecyclerView(jokes);
            }
        });

    }
}
