package com.example.ubuntu.bick.tvapplication.presenter;

import com.example.ubuntu.bick.tvapplication.base.Basepresenter;
import com.example.ubuntu.bick.tvapplication.model.WeatherModel;
import com.example.ubuntu.bick.tvapplication.view.WeatherView;

/**
 * Created by ubuntu on 18-2-1.
 */

public class WeatherPresenter extends Basepresenter<WeatherView> {


    private WeatherView weatherView;
    private WeatherModel model;

    /**
     * 绑定View
     * @param weatherView
     */
    public WeatherPresenter(WeatherView weatherView) {
        super(weatherView);

        this.weatherView=weatherView;
        model=new WeatherModel();
    }

    public void connect(){

        model.getNetWork();
    }


}
