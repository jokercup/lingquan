package com.example.lingquan.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RenChao on 2020/5/9.
 */
public class RetrofitManger {
    private static final RetrofitManger ourInstance = new RetrofitManger();
    private final Retrofit mMretrofit;

    public static RetrofitManger getInstance() {
        return ourInstance;
    }

    private RetrofitManger() {
        //创建retrofit
        mMretrofit = new Retrofit.Builder()
                .baseUrl(Constans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  Retrofit getMretrofit(){
        return  mMretrofit;
    }
}
