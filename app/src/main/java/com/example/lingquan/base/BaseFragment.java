package com.example.lingquan.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lingquan.R;
import com.example.lingquan.coustom.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by RenChao on 2020/5/8.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private State type = State.NONE;
    private View mSuceessciew;
    private View mLoadingview;
    private View mErrorview;
    private View mEmptyview;
    private View mStateview;

    public enum State {
        NONE, LOADING, ERROR, SUCCESS, EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseframelayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //其他状态下的界面
        mStateview = loadrootview(inflater,container);
        mBaseframelayout = mStateview.findViewById(R.id.base_container);
        //加载状态页面
        loadotherview(inflater, container);
        //绑定
        mBind = ButterKnife.bind(this, mStateview);
        initview(mStateview);
        initListison();
        initPersenter();
        loaddata();

        return mStateview;
    }



    @OnClick(R.id.network_wifi)
    public void retry() {

        Log.d(TAG, "retry: ----->"+"点击重试....");
        //点击重新加载内容
        onRetryClick();

    }

    /**
     * 如果子fragment需要知道网络错误以后的点击，那覆盖些方法即可
     */
    protected void onRetryClick() {

    }


    protected  View loadrootview(LayoutInflater inflater,ViewGroup container){
        return inflater.inflate(R.layout.base_fragment_null, container, false);
    };




    /**
     * 子类设置监听事件
     */
    protected void initListison() {

    }

    /**
     * 加载各种状态view
     *
     * @param inflater
     * @param container
     */
    private void loadotherview(LayoutInflater inflater, ViewGroup container) {

        //成功获取数据
        mSuceessciew = loadview(inflater, container);
        mBaseframelayout.addView(mSuceessciew);
        //加载中...
        mLoadingview = loadingview(inflater, container);
        mBaseframelayout.addView(mLoadingview);
        //错误页面
        mErrorview = errorview(inflater, container);
        mBaseframelayout.addView(mErrorview);
        //空页面
        mEmptyview = emptyview(inflater, container);
        mBaseframelayout.addView(mEmptyview);

        setstates(State.NONE);

    }

    private View emptyview(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.empty_fragment, container, false);

        return view;
    }

    private View errorview(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.error_fragment, container, false);

        return view;
    }

    private View loadingview(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.loading_fragment, container, false);
    }

    /**
     * 外部调用，子类通过这个方法修改UI
     *
     * @param state
     */
    protected void setstates(State state) {
        this.type = state;
        if (type == state.SUCCESS) {
            mSuceessciew.setVisibility(View.VISIBLE);
        } else {
            mSuceessciew.setVisibility(View.GONE);
        }
        if (type == state.LOADING) {
            mLoadingview.setVisibility(View.VISIBLE);
        } else {
            mLoadingview.setVisibility(View.GONE);
        }

        mEmptyview.setVisibility(state == State.EMPTY ? View.VISIBLE : View.GONE);
        mErrorview.setVisibility(state == State.ERROR ? View.VISIBLE : View.GONE);
    }

    protected void initview(View view) {
    }

    protected void loaddata() {
        /**
         * 加载数据
         */

    }

    protected void initPersenter() {
        /**
         * 初始化
         */
    }

    protected View loadview(LayoutInflater inflater, ViewGroup container) {
        int resId = getrootviewId();
        return inflater.inflate(resId, container, false);

    }

    protected abstract int getrootviewId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
        release();
    }

    /**
     * 释放资源
     */
    protected void release() {

    }
}
