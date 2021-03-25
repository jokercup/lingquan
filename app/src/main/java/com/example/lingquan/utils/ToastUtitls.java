package com.example.lingquan.utils;

import android.widget.Toast;

import com.example.lingquan.base.BaseApplication;

/**
 * Created by RenChao on 2020/5/13.
 */
public class ToastUtitls {
    private static Toast toast;
    public static void showToast(String msg){
        if (toast==null) {
            toast=  Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();

    }
}
