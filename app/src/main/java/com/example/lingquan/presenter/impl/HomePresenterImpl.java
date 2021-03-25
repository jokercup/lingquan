package com.example.lingquan.presenter.impl;

import android.util.Log;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.Categoryies;
import com.example.lingquan.presenter.IhomePresenter;
import com.example.lingquan.utils.LogUtils;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.view.IhomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/9.
 */
public class HomePresenterImpl implements IhomePresenter {
    private static final String TAG = "HomePresenterImpl";
    private IhomeCallback mIhomeCallback=null;
    @Override
    public void getCategoies() {
        if (mIhomeCallback!=null) {
            mIhomeCallback.onLoading();
        }
        Retrofit retrofit= RetrofitManger.getInstance().getMretrofit();
        Api api = retrofit.create(Api.class);
        Call<Categoryies> task = api.getcategory();
        task.enqueue(new Callback<Categoryies>() {
            @Override
            public void onResponse(Call<Categoryies> call, Response<Categoryies> response) {
                //数据结果
                int code=response.code();
                Log.d(TAG,"----"+code);
                if (code== HttpURLConnection.HTTP_OK) {
                    Categoryies ca=response.body();

                    Log.d(TAG,"----"+ca.toString());
                    if (mIhomeCallback!=null) {
                        if (ca==null&&ca.getData().size()==0) {
                            mIhomeCallback.onEmpty();
                        }else{
                            mIhomeCallback.onCategoires(ca);
                        }
                    }

                }else{
                    Log.d(TAG,"请求失败...");
                    if (mIhomeCallback!=null) {
                        mIhomeCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categoryies> call, Throwable t) {
                Log.d(TAG,"请求错误..."+t);
                if (mIhomeCallback!=null) {
                    mIhomeCallback.onError();
                }
            }
        });

    }



    @Override
    public void regiesterViewCallback(IhomeCallback mcallback) {
        this.mIhomeCallback=mcallback;
    }

    @Override
    public void unregiterViewCallback(IhomeCallback mcallback) {
        mIhomeCallback=null;
    }
}
