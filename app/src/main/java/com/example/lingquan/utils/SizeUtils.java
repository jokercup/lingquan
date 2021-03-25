package com.example.lingquan.utils;

import android.content.Context;

/**
 * Created by RenChao on 2020/5/12.
 */
public class SizeUtils {
    public  static int dip2px(Context context,float dpvalue){

        float scale=context.getResources().getDisplayMetrics().density;


        return (int)(dpvalue*scale+0.5f);
    }

}
