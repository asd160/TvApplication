package com.example.ubuntu.bick.tvapplication.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * User:白二鹏
 * Created by Administrator-11-14 14 : 32
 */

public class DialogUtils {

    //防止外部实例化
    private DialogUtils() {
    }

    private ProgressDialog progresswDialog;

    //Holder持有者
    private static class DialogUtilsHolder{
        //实例化出外层的DialogUtils
        static DialogUtils dialogUtils=new DialogUtils();
    }

    public static DialogUtils getInstance(){
        return DialogUtilsHolder.dialogUtils;
    }

    /**
     * 显示 进度对话框
     * @param context
     * @param msg
     */
    public void show(Context context,String msg){

        close();
        //对ProgressDialog 初始化配置
        createProgressDialog(context,msg);
        //显示
        if(progresswDialog!=null&&progresswDialog.isShowing()){
            progresswDialog.show();
        }

    }
    private void createProgressDialog(Context context,String msg){
        progresswDialog=new ProgressDialog(context);
        progresswDialog.setCancelable(false);
        progresswDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progresswDialog.setMessage(msg);
        progresswDialog.show();
    }

    public void close(){
        if(progresswDialog!=null&&progresswDialog.isShowing()){
            progresswDialog.dismiss();
        }
    }




}
