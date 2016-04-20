package com.bitech.joke.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitech.joke.R;
import com.bumptech.glide.Glide;

/**
 * <p></p>
 * Created on 2016/4/11 14:55.
 *
 * @author Lucy
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    private SparseArray<View> views;

    public BaseRecyclerViewHolder(Context context, View contentView) {
        super(contentView);
        this.context = context;
        views = new SparseArray<View>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public void setText(int viewId, String text) {
        TextView textView = getTextView(viewId);
        textView.setText(text);
    }

    public void setImageView(int viewId, Bitmap bitmap) {
        ImageView imageView = getImageView(viewId);
        imageView.setImageBitmap(bitmap);
    }

    public void setImageView(Context context, int viewId, String url) {
        if (url.endsWith(".gif")) {//gif图片
            Glide.with(context).load(url).asGif().crossFade().centerCrop()
                    .placeholder(R.drawable.ic_loading_small_bg).error(R.drawable.ic_fail).into(getImageView(viewId));
        } else {
            Glide.with(context).load(url).crossFade().centerCrop().placeholder(R.drawable.ic_loading_small_bg)
                    .error(R.drawable.ic_fail).into(getImageView(viewId));
        }
    }
}
