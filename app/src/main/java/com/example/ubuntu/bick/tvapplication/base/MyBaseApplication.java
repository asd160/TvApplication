package com.example.ubuntu.bick.tvapplication.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by ubuntu on 18-2-1.
 */

public class MyBaseApplication extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;
    }
}
