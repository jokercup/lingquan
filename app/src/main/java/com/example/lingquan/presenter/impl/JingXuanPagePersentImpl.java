package com.example.lingquan.presenter.impl;

import android.util.Log;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.model.domain.JingxuanContent;
import com.example.lingquan.presenter.IjingxuanPagePersenter;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.utils.URLutil;
import com.example.lingquan.view.IjingxuanCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/18.
 */
public class JingXuanPagePersentImpl implements IjingxuanPagePersenter {
    private static final String TAG = "JingXuanPagePersentImpl";
    private final Api mApi;


    private IjingxuanCallBack mviewcallback = null;

    public JingXuanPagePersentImpl() {

        Retrofit mretrofit = RetrofitManger.getInstance().getMretrofit();
        mApi = mretrofit.create(Api.class);

    }
    @Override
    public void getcategoryies() {
        if (mviewcallback != null) {
            mviewcallback.onLoading();
        }
        //拿到retrofit
        Call<JingxuanBean> task = mApi.getjingxuanInfo();
        task.enqueue(new Callback<JingxuanBean>() {
            @Override
            public void onResponse(Call<JingxuanBean> call, Response<JingxuanBean> response) {
                Log.d(TAG, "onResponse: --->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "onResponse: ---->" + response.body());
                    //成功通知UI
                    JingxuanBean result = response.body();
                    if (mviewcallback != null) {
                        mviewcallback.onCategoryiesLoad(result);
                    }

                } else {
                    handlerOnloaderror();

                }

            }


            @Override
            public void onFailure(Call<JingxuanBean> call, Throwable t) {
                handlerOnloaderror();
            }
        });


    }

    private void handlerOnloaderror() {
        if (mviewcallback != null) {
            mviewcallback.onError();
        }
    }

    @Override
    public void ongetcategoryCOnten(JingxuanBean.DataBean jx) {

        String id = URLutil.getjingxuanURL(jx.getFavorites_id());
        Log.d(TAG, "ongetcategoryCOnten: id---->" + id);
        Call<JingxuanContent> task = mApi.getjingxuanContent(id);
        task.enqueue(new Callback<JingxuanContent>() {
            @Override
            public void onResponse(Call<JingxuanContent> call, Response<JingxuanContent> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "ongetcategoryCOnten: ---->" + response.body());
                    //成功通知UI
                    JingxuanContent result = response.body();
                    if (mviewcallback != null) {
                        mviewcallback.oncategoryContent(result);
                    }

                } else {
                    handlerOnloaderror();

                }
            }

            @Override
            public void onFailure(Call<JingxuanContent> call, Throwable t) {
                handlerOnloaderror();
            }
        });

    }

    @Override
    public void reloadContent() {

        Log.d(TAG, "reloadContent: ---->重新加载内容");
        this.getcategoryies();

    }

    @Override
    public void regiesterViewCallback(IjingxuanCallBack mcallback) {
        this.mviewcallback = mcallback;

    }

    @Override
    public void unregiterViewCallback(IjingxuanCallBack mcallback) {
        this.mviewcallback = null;
    }
}
