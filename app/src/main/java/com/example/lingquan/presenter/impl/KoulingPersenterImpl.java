package com.example.lingquan.presenter.impl;

import android.util.Log;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.KoulingParms;
import com.example.lingquan.model.domain.KoulingResult;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.utils.URLutil;
import com.example.lingquan.view.IkoulingCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/15.
 */
public class KoulingPersenterImpl implements IkoulingPresenter {

    private static final String TAG = "KoulingPersenterImpl";
    private IkoulingCallBack mkoulingcallback = null;

    private LoadStates currestate = LoadStates.LOADING;
    private String mcover;
    private KoulingResult mKr;

    enum LoadStates {
        LOADING, SUCCESS, ERROR, NONE
    }


    @Override
    public void getkouling(String title, String url, String cover) {
        this.onkoulingLoading();
        this.mcover=cover;
        Log.d(TAG, "getkouling: --->" + title);
        Log.d(TAG, "getkouling: --->" + url);
        Log.d(TAG, "getkouling: --->" + cover);
        String koulingUrl = URLutil.getKoulingUrl(url);
        Log.d(TAG, "getkouling: --->" + koulingUrl);

        Retrofit retrofit = RetrofitManger.getInstance().getMretrofit();
        Api api = retrofit.create(Api.class);

        KoulingParms kp = new KoulingParms(koulingUrl, "");

        Call<KoulingResult> task = api.getkoulingconten(kp);
        task.enqueue(new Callback<KoulingResult>() {

            @Override
            public void onResponse(Call<KoulingResult> call, Response<KoulingResult> response) {

                Log.d(TAG, "onResponse: ---->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    //请求成功
                    mKr = response.body();
                    Log.d(TAG, "onResponse: ---->" + mKr);
                    //通知更新UI
                    onKoulingSuccess();
                } else {
                    //请求失败
                    onloaderror();
                }

            }


            @Override
            public void onFailure(Call<KoulingResult> call, Throwable t) {
                //请求失败
                onloaderror();

            }
        });

    }

    private void onKoulingSuccess() {
        if (mkoulingcallback != null) {
            Log.d(TAG, "onKoulingSuccess: "+mcover+"---------------"+ mKr.getData().toString());
            mkoulingcallback.onKoulingLoad(mcover, mKr);
        }else {
            currestate=LoadStates.SUCCESS;
        }
    }


    private void onloaderror() {
        if (mkoulingcallback != null) {
            mkoulingcallback.onError();
        }else{
            currestate=LoadStates.ERROR;
        }
    }

    @Override
    public void regiesterViewCallback(IkoulingCallBack mcallback) {
        this.mkoulingcallback = mcallback;
        if (currestate != LoadStates.NONE) {
            if (currestate == LoadStates.SUCCESS) {
                onKoulingSuccess();
            } else if (currestate == LoadStates.ERROR) {
                onloaderror();
            } else if (currestate == LoadStates.LOADING) {
                onkoulingLoading();
            }

        }

    }

    @Override
    public void unregiterViewCallback(IkoulingCallBack mcallback) {

        this.mkoulingcallback = null;


    }

    private void onkoulingLoading() {
        if (mkoulingcallback != null) {
            mkoulingcallback.onLoading();
        }else{
            currestate=LoadStates.LOADING;
        }

    }
}
