package com.example.lingquan.presenter.impl;

import android.util.Log;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.presenter.ICategroyPagePresenter;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.utils.URLutil;
import com.example.lingquan.view.ICategroPageCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/11.
 */
public class ICategroyPageImpl implements ICategroyPagePresenter {
    private static final String TAG = "ICategroyPageImpl";

    public static final int DEFAULT_PAGE = 1;
    public Map<Integer, Integer> pageinfo = new HashMap<>();
    private Integer mCrrenpage;



    @Override
    public void getCartegroyID(int categroyid) {
        for (ICategroPageCallback iCategroPageCallback : icallback) {
            if (iCategroPageCallback.getmateriaID() == categroyid) {
                iCategroPageCallback.onLoading();
            }

        }
        //根据分类ID加载内容

        Integer targpage = pageinfo.get(categroyid);
        if (targpage == null) {
            pageinfo.put(categroyid, DEFAULT_PAGE);
            targpage = DEFAULT_PAGE;
        }

        Call<PageCategory> task = getPageCategoryCall(categroyid, targpage);
        task.enqueue(new Callback<PageCategory>() {
            @Override
            public void onResponse(Call<PageCategory> call, Response<PageCategory> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {

                    PageCategory mdata = response.body();
                    Log.d(TAG, "onResponse: " + mdata.toString());

                    handlerPageHomecontent(mdata, categroyid);
                } else {
                    handlerNetworkerro(categroyid);
                }
            }

            @Override
            public void onFailure(Call<PageCategory> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });

    }

    private Call<PageCategory> getPageCategoryCall(int categroyid, Integer targpage) {
        String pageurl = URLutil.pageurl(categroyid, targpage);
        Log.d(TAG, "url: " + pageurl);
        Retrofit retrofit = RetrofitManger.getInstance().getMretrofit();
        Api api = retrofit.create(Api.class);
        return api.getPagecategroyContent(pageurl);
    }

    private void handlerNetworkerro(int categroyid) {
        for (ICategroPageCallback iCategroPageCallback : icallback) {
            if (iCategroPageCallback.getmateriaID() == categroyid) {
                iCategroPageCallback.onError();
            }

        }
    }

    private void handlerPageHomecontent(PageCategory content, int categroyid) {
        List<PageCategory.DataBean> data = content.getData();
        //通知更新UI
        for (ICategroPageCallback iCategroPageCallback : icallback) {
            if (iCategroPageCallback.getmateriaID() == categroyid) {
                if (content == null || content.getData().size() == 0) {
                    iCategroPageCallback.onEmpty();

                } else {
                    List<PageCategory.DataBean> looperdata = data.subList(data.size() - 5, data.size());
                    iCategroPageCallback.onbannerload(looperdata);
                    iCategroPageCallback.ongetContentload(data);
                }

            }
        }
    }

    @Override
    public void loadmore(int categroyid) {
        //记载更多数据
        mCrrenpage = pageinfo.get(categroyid);
        if (pageinfo == null) {
            mCrrenpage = 1;

        }
        //页面++
        mCrrenpage++;

        Call<PageCategory> task = getPageCategoryCall(categroyid, mCrrenpage);
        task.enqueue(new Callback<PageCategory>() {
            @Override
            public void onResponse(Call<PageCategory> call, Response<PageCategory> response) {
                PageCategory body = response.body();
                Log.d(TAG, "onResponse: -->"+body.getData().toString());
                handlerloadmoreresult(body,categroyid);

            }

            @Override
            public void onFailure(Call<PageCategory> call, Throwable t) {
                handlerloadmore(categroyid);

            }
        });
    }

    private void handlerloadmoreresult(PageCategory body,int categroyid) {
        for(ICategroPageCallback callback:icallback){
            if (callback.getmateriaID()==categroyid) {
                if (callback==null||body.getData().size()==0) {
                    callback.onloadmoreEmpty();
                }else{
                    callback.onloadmoreload(body.getData());
                }
            }
        }
    }

    private void handlerloadmore(int categroyid) {
        mCrrenpage--;
        pageinfo.put(categroyid, mCrrenpage);
        for(ICategroPageCallback callback:icallback){
            if (callback.getmateriaID()==categroyid) {
                callback.onloadmoreError();
            }
        }

    }

    @Override
    public void reload(int categroyid) {

    }

    public List<ICategroPageCallback> icallback = new ArrayList<>();

    @Override
    public void regiesterViewCallback(ICategroPageCallback mcallback) {
        //判断是否存在
        if (!icallback.contains(mcallback)) {
            icallback.add(mcallback);
        }

    }

    @Override
    public void unregiterViewCallback(ICategroPageCallback mcallback) {
        icallback.remove(mcallback);
    }
}
