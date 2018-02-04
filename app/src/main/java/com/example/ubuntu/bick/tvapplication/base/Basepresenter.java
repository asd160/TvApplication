package com.example.ubuntu.bick.tvapplication.base;

/**
 * User:白二鹏
 * Created by Administrator-11-13 16 : 07
 */

public class Basepresenter<V>  {

    private V basepresenter;

    /**
     * 绑定View
     * @param basepresenter
     */
    public Basepresenter(V basepresenter) {
        this.basepresenter = basepresenter;
    }

    public void detach(){
        this.basepresenter=null;
    }
}
