package com.bitech.joke.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bitech.joke.app.App;

/**
 * <p>SharePreferences的操作</p>
 * Created on 2016/4/5 14:22.
 *
 * @author Lucy
 */
public class SpUtil {

    private static final String SHARED_PREFERENCES_NAME="APP";

    public static SharedPreferences getSharedPrefences(){
     return App.getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static String getString(String key){
        return getSharedPrefences().getString(key,"");
    }
    public static void setString(String key,String value){
        getSharedPrefences().edit().putString(key,value).commit();
    }
    public static boolean getBoolean(String key){
        return getSharedPrefences().getBoolean(key,false);
    }
    public static void setBoolean(String key,boolean value){
        getSharedPrefences().edit().putBoolean(key,value).commit();
    }
    public static int getInt(String key){
        return getSharedPrefences().getInt(key,0);
    }
    public static void setInt(String key,int value){
        getSharedPrefences().edit().putInt(key,value).commit();
    }
    public static float getFloat(String key){
        return getSharedPrefences().getFloat(key,0f);
    }
    public static void setFloat(String key,float value){
        getSharedPrefences().edit().putFloat(key,value).commit();
    }
    public static long getLong(String key){
        return getSharedPrefences().getLong(key,0L);
    }
    public static void setLong(String key,long value){
        getSharedPrefences().edit().putLong(key,value).commit();
    }
}
