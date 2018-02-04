package com.example.ubuntu.bick.tvapplication.view;

import com.example.ubuntu.bick.tvapplication.bean.WeatherBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by ubuntu on 18-2-1.
 */

public interface WeatherService  {

    @GET("info/suggestion")
    Flowable<WeatherBean> weather();
}
