package com.example.ubuntu.bick.tvapplication.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * User:白二鹏
 * Created by Administrator-11-13 16 : 29
 */

public abstract class BaseActivity<P extends Basepresenter> extends FragmentActivity implements View.OnClickListener{

    private boolean isStatus=false;//沉浸式
    private boolean isShowActionBar =false;//ActionBar
    private boolean isFullScreen=false;//是否支持全屏


    private P presenter;
    public abstract int getLayoutId();
    public abstract P initPresenter();


    public abstract void setListener();//监听事件
    public abstract void myclick(View v);//点击事件回调方法

    public abstract void initView();//初始化事件
    public abstract void initData();//初始化事件
    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        presenter=initPresenter();

        init(); //初始化
        initView();//初始化View
        setListener(); //监听事件
        initData();//初始化Data

    }

    /**
     * 取消绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消
        if(presenter!=null){
            presenter.detach();
        }

    }

    /**
     * 设置透明状态 沉浸式
     */
    public void setStatus(boolean status){
        isStatus =status;
        if(isStatus){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 沉浸式 *Music
     */
    public void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置ActionBar 是否显示
     * @param showActionBar
     */
    public void setShowActionBar(boolean showActionBar){
        isShowActionBar=showActionBar;
        if(isShowActionBar){
        }else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 设置是否全屏
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen){
        isFullScreen=fullScreen;
        if(isFullScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 重写点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        //点击事件的回调  在那边会重写
        //那边的Click 就相当于点击事件了
        myclick(view);
    }

    /**
     * 显示Toast 土司 ~
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 无参数  跳转
     * @param clz
     */
    public void startActivity(Class<?> clz){
        Intent in=new Intent(this,clz);
        startActivity(in);
    }

    /**
     * 有参跳跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent in=new Intent(this,clz);
        in.putExtras(bundle);
        startActivity(in);
    }

}
