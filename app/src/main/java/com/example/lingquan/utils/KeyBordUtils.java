package com.example.lingquan.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by RenChao on 2020/5/25.
 */
public class KeyBordUtils {

    public static void hide(Context context, View view) {
        InputMethodManager  im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        im.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static void show(Context context, View view) {
        InputMethodManager  im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        im.showSoftInput(view, 0);

    }
}
