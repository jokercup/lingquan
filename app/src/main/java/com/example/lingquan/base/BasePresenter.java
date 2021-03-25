package com.example.lingquan.base;

import com.example.lingquan.view.ICategroPageCallback;

/**
 * Created by RenChao on 2020/5/11.
 */
public interface BasePresenter<T> {

    /**
     * 注册回调
     * @param mcallback
     */
    void regiesterViewCallback(T mcallback);

    /**
     * 取消回调
     * @param mcallback
     */
    void unregiterViewCallback(T mcallback);
}
