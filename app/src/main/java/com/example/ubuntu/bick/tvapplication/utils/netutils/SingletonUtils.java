package com.example.ubuntu.bick.tvapplication.utils.netutils;

/**
 * User:白二鹏
 * Created by Administrator-11-29 11 : 32
 */

public abstract  class SingletonUtils<T> {
    private T instance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (instance == null) {
            synchronized (SingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
