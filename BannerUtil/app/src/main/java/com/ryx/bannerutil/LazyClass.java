package com.ryx.bannerutil;

/**
 * Created by TWh on 2017/11/22.
 */

public class LazyClass {
    private  static LazyClass instanse = null;
    //私有构造方法防止被实例化
    private  LazyClass(){

    }
    /* 1:懒汉式，静态工程方法，创建实例 */
    public static LazyClass getInstanse(){
        if (instanse==null){
            synchronized (LazyClass.class){
                if(instanse ==null){
                    instanse = new LazyClass();
                }
            }
        }
        return instanse;
    }
}
