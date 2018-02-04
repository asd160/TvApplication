package com.example.ubuntu.bick.tvapplication.view;

import com.example.ubuntu.bick.tvapplication.base.BaseView;

/**
 * Created by ubuntu on 18-2-1.
 */

public interface WeatherView  extends BaseView {

    void success(String mes);
    void faile();
    void other();

}
