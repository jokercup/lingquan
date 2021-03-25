package com.example.lingquan.utils;

import android.util.Log;

/**
 * Created by RenChao on 2020/5/8.
 */
public class LogUtils {

    public static final int currentLev = 4;
    public static final int debugtLev = 4;
    public static final int infoLev = 3;
    public static final int warningLev = 2;
    public static final int errorLev = 1;

    public static void  d(Object obj,String msg){
        if(currentLev>debugtLev){
            Log.d(obj.getClass().getSimpleName(), msg);
        }

    }
    public static void  e(Object obj,String msg){
        if(currentLev>errorLev){
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }
    public static void  i(Object obj,String msg){
        if(currentLev>infoLev){
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }
    public static void  w(Object obj,String msg){
        if(currentLev>warningLev){
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }

}
