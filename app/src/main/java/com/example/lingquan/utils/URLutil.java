package com.example.lingquan.utils;

import android.util.Log;

/**
 * Created by RenChao on 2020/5/11.
 */
public class URLutil {

    public static String pageurl(int id, int page) {

        return "discovery/" + id + "/" + page;
    }
    //计算图片大小，提升获取图片获取速度和响应

    public static String getcoverurl(String pict_url, int size) {
        return "https:" + pict_url + "_" + size + "x" + size + ".jpg";
    }

    public static String getcoverurl(String pict_url) {


        if (pict_url.startsWith("http") || pict_url.startsWith("https")) {
            return pict_url;
        } else {
            return "https:" + pict_url;
        }
    }

    public static String getKoulingUrl(String url) {
        if (url.startsWith("http") || url.startsWith("https")) {
            return url;
        } else {
            return "https:" + url;
        }
    }

    public static String getjingxuanURL(int id) {
        return "recommend/" + id;


    }

    public static String getonsehllurl(int page) {
        return "onSell/" + page;
    }
}

