package com.example.ubuntu.bick.tvapplication.model;

import com.example.ubuntu.bick.tvapplication.bean.WeatherBean;
import com.example.ubuntu.bick.tvapplication.common.BaseApi;
import com.example.ubuntu.bick.tvapplication.view.WeatherService;

import org.reactivestreams.Subscription;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ubuntu on 18-2-1.
 */

public class WeatherModel {

    public void getNetWork(){
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseApi.BASE_URL)
                .build();

        WeatherService service =retrofit.create(WeatherService.class);

        service.weather()
        .subscribeOn(Schedulers.io())//请求在子线程 //订阅在子线程（耗时） 观察在主线程（观察吧）
        .observeOn(AndroidSchedulers.mainThread())//请求后在主线程
                //subscribe() 订阅 结合起来 ， 被观察者 Observable  观察者Observer
                //订阅是件 开始 (Observable.subscribe(observer) 被观察者 订阅 观察者 反)
        .
            subscribe(new FlowableSubscriber<WeatherBean>() {
            @Override
            public void onSubscribe(Subscription s) {
            }
            @Override
            public void onNext(WeatherBean weatherBean) {

            }
            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        })

//        subscribeWith(new DisposableSubscriber<WeatherBean>() {
//        @Override
//        public void onNext(WeatherBean weatherBean) {
//            System.out.println("wwwwwwwwwwwwssssssssssssssssssss");
//        }
//
//        @Override
//        public void onError(Throwable t) {
//            System.out.println("wwwwwwwwwwwweeeeeeeeeeeeee");
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    })

        ;
    }
}
