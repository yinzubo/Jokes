package com.bitech.joke.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;

import com.bitech.joke.R;
import com.bitech.joke.base.BaseRecyclerAdapter;
import com.bitech.joke.base.BaseRecyclerViewHolder;
import com.bitech.joke.bean.JokeBean;
import com.bitech.joke.module.main.activity.PhotoViewActivity;

import java.util.List;

/**
 * <p></p>
 * Created on 2016/4/19 14:30.
 *
 * @author Lucy
 */
public class HomeRecyclerAdapter extends BaseRecyclerAdapter<JokeBean.Joke> {

    public HomeRecyclerAdapter(Context context, List<JokeBean.Joke> datas) {
        super(context, datas);
    }

    @Override

    protected int getItemLayoutId() {
        return R.layout.fragment_home_recylerview_item;
    }

    @Override
    protected void bindData(final BaseRecyclerViewHolder holder, int position, final JokeBean.Joke item) {

        if(!TextUtils.isEmpty(item.url)){
            holder.getImageView(R.id.home_recyclerview_item_img).setVisibility(View.VISIBLE);
            holder.setImageView(context,R.id.home_recyclerview_item_img,item.url);
            holder.getImageView(R.id.home_recyclerview_item_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PhotoViewActivity.class);
                    intent.putExtra("url",item.url);
                    context.startActivity(intent);

                    //5.0,待研究
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptionsCompat options=ActivityOptionsCompat.makeScaleUpAnimation(holder.itemView,
                                holder.itemView.getWidth()/2,
                                holder.itemView.getHeight()/2,
                                0,0);

                    }
                }
            });
        }
        holder.setText(R.id.home_recycleview_item_textview,item.content);
    }
}
