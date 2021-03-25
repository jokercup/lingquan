package com.example.lingquan.presenter.impl;

import android.util.Log;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.SellShop;
import com.example.lingquan.presenter.IonSellpagePresenter;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.utils.URLutil;
import com.example.lingquan.view.IonSellCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/20.
 */
public class SellShopPagePresenterImpl implements IonSellpagePresenter {
    private static final String TAG = "SellShopPagePresenterIm";

    public static final int DEFAULT_PAGE = 1;
    public int mcurrenpage = DEFAULT_PAGE;
    private IonSellCallBack sellshopcallback = null;
    private final Api mApi;

    public SellShopPagePresenterImpl() {

        Retrofit retrofit = RetrofitManger.getInstance().getMretrofit();
        mApi = retrofit.create(Api.class);

    }

    @Override
    public void getsellshopcontent() {
        Log.d(TAG, "getsellshopcontent: -----"+isloading);

        if (isloading) {
            return;
        }
        if (sellshopcallback != null) {
            sellshopcallback.onLoading();
        }
        isloading=true;

        String sehllurl = URLutil.getonsehllurl(mcurrenpage);
        Log.d(TAG, "getsellshopcontent: -----"+sehllurl);

        Call<SellShop> task = mApi.getsellshop(sehllurl);
        task.enqueue(new Callback<SellShop>() {
            @Override
            public void onResponse(Call<SellShop> call, Response<SellShop> response) {
                isloading=false;
                Log.d(TAG, "onResponse: ---->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    SellShop sellShop = response.body();
                    //数据加载成功
                    onSuccess(sellShop);

                } else {
                    //数据加载失败
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SellShop> call, Throwable t) {
                //数据加载失败
                onError();
            }
        });

    }


    private void onSuccess(SellShop sellShop) {
        if (sellshopcallback != null) {
            int size = sellShop.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
            try {
                if (size == 0) {
                    onEmpty();

                } else {
                    sellshopcallback.onContentload(sellShop);
                }
            } catch (Exception e) {
                e.printStackTrace();
                onEmpty();
            }

        }
    }


    private void onEmpty() {
        if (sellshopcallback != null) {
            sellshopcallback.onEmpty();
        }
    }

    private void onError() {
        isloading=false;
        if (sellshopcallback != null) {
            sellshopcallback.onError();
        }
    }

    @Override
    public void reload() {
        //重新加载

        Log.d(TAG, "reload: -------");
        this.getsellshopcontent();

    }

    //当前加载状态
    private boolean isloading=false;

    @Override
    public void loadmore() {
        if (isloading) {
            return;
        }
        isloading=true;
        mcurrenpage++;
        String url = URLutil.getonsehllurl(mcurrenpage);
        Call<SellShop> task = mApi.getsellshop(url);
        task.enqueue(new Callback<SellShop>() {
            @Override
            public void onResponse(Call<SellShop> call, Response<SellShop> response) {
                Log.d(TAG, "onResponse: ---->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    isloading=false;
                    SellShop sellShop = response.body();
                    Log.d(TAG, "onMoreLoad: "+sellShop.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size());
                    //数据加载更多成功
                    onmoreLoad(sellShop);

                } else {
                    //数据加载更多失败
                    onMoreLoadError();
                }
            }

            @Override
            public void onFailure(Call<SellShop> call, Throwable t) {
                //数据加载更多失败
                onMoreLoadError();
            }
        });

    }

    //加载更多错误

    private void onMoreLoadError() {
        mcurrenpage--;
        if (sellshopcallback != null) {
            sellshopcallback.onmorloadError();
        }
    }

    //加载更多

    private void onmoreLoad(SellShop sellShop) {
        if (sellshopcallback != null) {
            int size = sellShop.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
            if (size != 0) {
                sellshopcallback.onMoreLoad(sellShop);
            } else {
                mcurrenpage--;
                sellshopcallback.onmorloadError();
            }

        }

    }

    //注册
    @Override
    public void regiesterViewCallback(IonSellCallBack mcallback) {
        this.sellshopcallback = mcallback;
    }

    @Override
    public void unregiterViewCallback(IonSellCallBack mcallback) {
        this.sellshopcallback = null;
    }
}
