package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.view.IhomeCallback;

/**
 * Created by RenChao on 2020/5/9.
 */
public interface IhomePresenter extends BasePresenter<IhomeCallback> {
    /**
     * 获取商品分类
     */
    void getCategoies();

}
