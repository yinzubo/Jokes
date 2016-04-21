package com.bitech.joke.module.main.view;

import com.bitech.joke.base.BaseView;
import com.bitech.joke.bean.JokeBean;

import java.util.List;

/**
 * <p></p>
 * Created on 2016/4/19 14:46.
 *
 * @author Lucy
 */
public interface IHomeView extends BaseView{

    public void updateRecyclerView(List<JokeBean.Joke> jokes);
}
