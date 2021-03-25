package com.example.lingquan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lingquan.base.BaseApplication;
import com.example.lingquan.model.domain.Cachewithduration;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.SyncFailedException;

/**
 * Created by RenChao on 2020/5/21.
 */
public class JsonCacheUtil {
    private static final String JSON_CACHE_NAME = "json_cache_name";
    private final Gson mGson;

    private final SharedPreferences mSharedPreferences;


    private JsonCacheUtil() {
        mSharedPreferences = BaseApplication.getContext().getSharedPreferences(JSON_CACHE_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    private static JsonCacheUtil jsonCacheUtil = null;

    public static JsonCacheUtil getInstance() {
        if (jsonCacheUtil == null) {
            jsonCacheUtil = new JsonCacheUtil();
        }
        return jsonCacheUtil;

    }


    public void savecache(String key, Object value) {
        this.savecache(key, value, -1l);

    }

    public void savecache(String key, Object value, long milling) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        String jsonstr = mGson.toJson(value);
        if (milling != -1) {
            //系统当前时间
            milling += System.currentTimeMillis();
        }
        //保存一个有数据有时间的内容
        Cachewithduration cachewithduration = new Cachewithduration(milling, jsonstr);
        String cachestr = mGson.toJson(cachewithduration);

        edit.putString(key, cachestr);
        edit.apply();
    }


    public void deletecache(String key) {
        mSharedPreferences.edit().remove(key).apply();

    }

    public <T> T getvalue(String key, Class<T> clazzs) {
        String value = mSharedPreferences.getString(key, null);
        if (value == null) {
            return null;
        }
        Cachewithduration cachewithduration = mGson.fromJson(value, Cachewithduration.class);
        //对时间进行判断
        long dureatin = cachewithduration.getDureatin();
        //判断是否过期
        if (dureatin != -1 && dureatin - System.currentTimeMillis() <= 0) {

            //过期
            return null;
        } else {

            //未过期
            String cache = cachewithduration.getCache();
            T t = mGson.fromJson(cache, clazzs);

            return t;

        }
    }
}
