package com.example.ubuntu.bick.tvapplication.utils.screenUtils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

import com.example.ubuntu.bick.tvapplication.base.MyBaseApplication;

import java.lang.reflect.Method;

/**
 * Created by ubuntu on 18-2-2.
 */

public  class  ScreenGet {

    private Activity context;

    public ScreenGet(Activity contextc) {
        this.context = contextc;
    }

    /**
     * 获取底部虚拟键盘的高度
     */
    public int getBottomKeyboardHeight(){
        int screenHeight =  getAccurateScreenDpi()[1];
        DisplayMetrics dm = new DisplayMetrics();
        this.context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightDifference = screenHeight - dm.heightPixels;
        return heightDifference;
    }

    /**
     * 获取精确的屏幕大小
     */
    private int[] getAccurateScreenDpi()
    {
        int[] screenWH = new int[2];
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class<?> c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, dm);
            screenWH[0] = dm.widthPixels;
            screenWH[1] = dm.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return screenWH;
    }
}
