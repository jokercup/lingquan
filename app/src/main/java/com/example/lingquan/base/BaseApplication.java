package com.example.lingquan.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by RenChao on 2020/5/13.
 */
public class BaseApplication extends Application {
    private static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext=getBaseContext();

    }

    public  static  Context getContext(){
        return  mcontext;
    }
}
