package com.bitech.joke.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * <p></p>
 * Created on 2016/4/12 17:39.
 *
 * @author Lucy
 */
public class ToastUtil {
    private static Toast toast;

    // 解决了toast频繁显示导致时间叠加问题
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
