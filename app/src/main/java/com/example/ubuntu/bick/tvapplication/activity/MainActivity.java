/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.ubuntu.bick.tvapplication.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubuntu.bick.tvapplication.R;
import com.example.ubuntu.bick.tvapplication.base.BaseActivity;
import com.example.ubuntu.bick.tvapplication.custom.LineGraphicView;
import com.example.ubuntu.bick.tvapplication.presenter.WeatherPresenter;
import com.example.ubuntu.bick.tvapplication.view.WeatherView;
import java.util.ArrayList;


/*
 * MainActivity class that loads {@link MainFragment}.
 */
public class MainActivity extends BaseActivity<WeatherPresenter> implements WeatherView {

    private LineGraphicView tuv,lgv_main;
    private ArrayList<Double> yListTall;//最高温度
    private ArrayList<Double> yListLow;//最低温度
    private TextView tv_wendu_sing;

    /**
     * 初始化数据
     */
    private void initDataa() {
        yListTall = new ArrayList<Double>();
        yListTall.add((double) 2);
        yListTall.add((double) 1);
        yListTall.add((double) 5);
        yListTall.add((double) 3);
        yListTall.add((double) 4);
        yListTall.add((double) 4);
        yListTall.add((double) 2);


        yListLow = new ArrayList<Double>();

        yListLow.add((double) 2);
        yListLow.add((double) 1);
        yListLow.add((double) 5);
        yListLow.add((double) 3);
        yListLow.add((double) 4);
        yListLow.add((double) 4);
        yListLow.add((double) 2);

//        ArrayList<String> xRawDatas = new ArrayList<String>();
//        xRawDatas.add("05-19");
//        xRawDatas.add("05-20");
//        xRawDatas.add("05-21");
//        xRawDatas.add("05-22");
//        xRawDatas.add("05-23");
//        xRawDatas.add("05-24");
//        xRawDatas.add("05-25");
//        xRawDatas.add("05-26");
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    public WeatherPresenter initPresenter() {
        return new WeatherPresenter(this);
    }

    @Override
    public void setListener() {
       // tv_wendu_sing.setOnClickListener(this);
    }

    @Override
    public void myclick(View v) {

        switch (v.getId()){
            case R.id.tv_wendu_sing:
                showToast("点击温度");
                break;
        }
    }

    @Override
    public void initView() {
        initViewa();
        initPresenter().connect();
    }

    @Override
    public void initData() {

        initDataa();
        int sizeMax=30;
        int sizeLow=25;
        int size=sizeMax-sizeLow;
        //tuv.setData(yListTall,yListLow, null, 30 , 5);
        lgv_main.setData(this,yListTall,yListLow, 10,1,
                sizeMax,sizeLow,size);
        lgv_main.setBheight(100);

    }

    @Override
    public void init() {

        initTvzhuangtailan();
    }

    /**
     *  初始化View
     */
    private void initViewa() {
        //tuv = findViewById(R.id.tuv);
        lgv_main=findViewById(R.id.lgv_main);
       // tv_wendu_sing = findViewById(R.id.tv_wendu_sing);
        ttfTools();//设置字体
    }

    private void ttfTools() {

        //修改字体
        //Typeface typeFace = Typeface.createFromAsset(getAssets(), "roboto.ttf");
        // 应用字体
        // tv_wendu_sing.setTypeface(typeFace);

    }

    private void initTvzhuangtailan() {
        Window window = this.getWindow();
       //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getColor(R.color.lb_browse_title_color));

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoging() {

    }

    @Override
    public void showFailure(String msg) {

    }

    @Override
    public void success(String mes) {

    }

    @Override
    public void faile() {

    }

    @Override
    public void other() {

    }
}
