package com.example.ubuntu.bick.tvapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * User:白二鹏
 * Created by Administrator-11-14 16 : 17
 *
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View view;

    public abstract void initView();
    public abstract void initData();
    public abstract int getLayoutId();
    public abstract void getLayouView(View v);


    public abstract void setListener();//监听事件
    public abstract void myclick(View v);//点击事件回调方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),getLayoutId(),null);
        getLayouView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();
        setListener();
        initData();
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
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 无参数  跳转
     * @param clz
     */
    public void startActivity(Class<?> clz){
        Intent in=new Intent(getActivity(),clz);
        startActivity(in);
    }

    /**
     * 有参跳跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent in=new Intent(getActivity(),clz);
        in.putExtras(bundle);
        startActivity(in);
    }


    public void detach(View v){
        v=null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.view=null;
    }


}

