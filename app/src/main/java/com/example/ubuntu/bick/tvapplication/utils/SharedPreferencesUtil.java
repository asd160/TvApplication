package com.example.ubuntu.bick.tvapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ubuntu.bick.tvapplication.base.MyBaseApplication;


/**
 * User:白二鹏
 * Created by Administrator-11-19 19 : 32
 */

public class SharedPreferencesUtil {

    private final static String SP_NAME="common_data";

    /**
     * 得到SharedPreferences 对象
     */

    public static SharedPreferences getPreferences(){

        return MyBaseApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存一行数据
     */
    public static void putPreference(String key,String value){
        SharedPreferences.Editor mEditor=getPreferences().edit();
        mEditor.putString(key,value);
        mEditor.commit();
    }

    /**
     * 获取uid 数据
     */
    public static String getPreferencesValue(String key){
        return getPreferences().getString(key,"");
    }

    /**
     * 清除指定数据
     */
    public static void clerarPreference(String key){
        SharedPreferences.Editor mEditor=getPreferences().edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 清空所有数据
     */
     public static void clearPreference(){
         SharedPreferences.Editor mEditor=getPreferences().edit();
         mEditor.clear();
         mEditor.commit();
     }

}
