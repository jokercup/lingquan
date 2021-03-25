package com.example.lingquan.presenter.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lingquan.model.Api;
import com.example.lingquan.model.domain.History;
import com.example.lingquan.model.domain.SeacherResult;
import com.example.lingquan.model.domain.Searchrecommend;
import com.example.lingquan.presenter.IseahcerPresenter;
import com.example.lingquan.utils.JsonCacheUtil;
import com.example.lingquan.utils.RetrofitManger;
import com.example.lingquan.view.IseacherCallBack;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RenChao on 2020/5/21.
 */
public class SeacherPersenterImpl implements IseahcerPresenter {
    private static final String TAG = "SeacherPersenterImpl";
    private static final String KEY_HISTORY = "key_history";
    private static final int DEFAULT_HIS_SIZE=10;
    public  int hismaxsize=DEFAULT_HIS_SIZE;

    private final Api mApi;
    private IseacherCallBack searchcallback = null;
    private static final int DEFAULT_PAGE = 1;
    private int currenpage = DEFAULT_PAGE;
    private String msearchword = null;
    private List<String> mList=null;
    private final JsonCacheUtil mInstance;

    public SeacherPersenterImpl() {
        Retrofit mretrofit = RetrofitManger.getInstance().getMretrofit();
        mApi = mretrofit.create(Api.class);
        mInstance = JsonCacheUtil.getInstance();
    }

    @Override
    public void getseacherHistory() {
        History his = mInstance.getvalue(KEY_HISTORY, History.class);
        if (searchcallback!=null) {
            searchcallback.onHistoryload(his);
        }
    }



    /**\
     * 删除历史记录
     */

    @Override
    public void deleteHistory() {
        mInstance.deletecache(KEY_HISTORY);
        if (searchcallback != null) {
            searchcallback.onHistoryDelete();
        }


    }



    /**
     * 添加历史记录
     *
     * @param value
     */

    public void saveHistory(String value) {

        History his = mInstance.getvalue(KEY_HISTORY, History.class);
        //如果有就去掉再添加
        List<String> historylist=null;
        if (his != null && his.getHistoryes() != null) {
            historylist = his.getHistoryes();
            if (historylist.contains(value)) {
                historylist.remove(value);
            }
        }
        //去重完成
        //处理没有数据的情况
        if (historylist == null) {
            historylist = new ArrayList<>();
        }
        if (his == null) {
            his = new History();
        }
        his.setHistoryes(historylist);
        //对个数限制
        if (historylist.size() > hismaxsize) {
             historylist = historylist.subList(0, hismaxsize);
        }

        //添加记录
        historylist.add(value);
        //保存记录
        mInstance.savecache(KEY_HISTORY,his);

    }


    @Override
    public void doseacher(String keywords) {
        if (msearchword == null || !msearchword.endsWith(keywords)) {
            this.saveHistory(keywords);
            this.msearchword = keywords;
        }
        if (searchcallback != null) {
            searchcallback.onLoading();
        }
        Call<SeacherResult> task = mApi.getseacher(currenpage, keywords);
        task.enqueue(new Callback<SeacherResult>() {
            @Override
            public void onResponse(Call<SeacherResult> call, Response<SeacherResult> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    SeacherResult body = response.body();

                    handseacherresult(body);
                } else {

                    onERROR();

                }
            }

            @Override
            public void onFailure(Call<SeacherResult> call, Throwable t) {
                t.printStackTrace();
                onERROR();
            }
        });

    }

    private void onERROR() {

        if (searchcallback != null) {
            searchcallback.onError();
        }
    }

    private void handseacherresult(SeacherResult body) {

        if (isResultEmpty(body)) {
            //数据为空
            searchcallback.onEmpty();

        } else {
            searchcallback.onseacherSuccess(body);
        }

    }

    private boolean isResultEmpty(SeacherResult body) {
        try {
            int size = body.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data().size();

            return body == null || size == 0;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void reseacher() {
        if (msearchword == null) {
            if (searchcallback == null) {
                searchcallback.onEmpty();
            }

        } else {
            //重新搜索
            this.doseacher(msearchword);
        }
    }

    @Override
    public void loadmore() {
        currenpage++;
        if (msearchword == null) {
            if (searchcallback == null) {
                searchcallback.onEmpty();
            }
        } else {
            doseachermore();
        }

    }

    private void doseachermore() {

        Call<SeacherResult> task = mApi.getseacher(currenpage, msearchword);
        task.enqueue(new Callback<SeacherResult>() {
            @Override
            public void onResponse(Call<SeacherResult> call, Response<SeacherResult> response) {

                if (response.code() == HttpURLConnection.HTTP_OK) {
                    SeacherResult body = response.body();

                    handmoreloadresult(body);
                } else {

                    onLoadmoreERROR();

                }
            }

            @Override
            public void onFailure(Call<SeacherResult> call, Throwable t) {
                t.printStackTrace();
                onLoadmoreERROR();
            }
        });
    }

    /**
     * 加载更多成功
     */


    private void handmoreloadresult(SeacherResult body) {

        if (isResultEmpty(body)) {
            //数据为空
            searchcallback.onmoreloadempty();

        } else {
            searchcallback.onmoreload(body);
        }
    }

    /**
     * 加载更多失败
     */
    private void onLoadmoreERROR() {
        currenpage--;
        if (searchcallback != null) {
            searchcallback.onmoreloaderror();
        }
    }

    @Override
    public void getrecommendwords() {
        Call<Searchrecommend> task = mApi.getrecommendword();
        task.enqueue(new Callback<Searchrecommend>() {
            @Override
            public void onResponse(Call<Searchrecommend> call, Response<Searchrecommend> response) {
                Log.d(TAG, "onResponse: ---->" + response.code());
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Searchrecommend body = response.body();
                    if (searchcallback != null) {
                        searchcallback.onrecommendwordsload(body.getData());
                    }

                }
            }

            @Override
            public void onFailure(Call<Searchrecommend> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    @Override
    public void regiesterViewCallback(IseacherCallBack mcallback) {

        this.searchcallback = mcallback;

    }

    @Override
    public void unregiterViewCallback(IseacherCallBack mcallback) {
        this.searchcallback = null;
    }
}
