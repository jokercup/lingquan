package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.view.IonSellCallBack;

/**
 * Created by RenChao on 2020/5/20.
 */
public interface IonSellpagePresenter extends BasePresenter<IonSellCallBack> {

    /**
     * 加载数据
     */
    void getsellshopcontent();

    /**
     * 重新加载
     */
    void reload();

    /**
     * 加载更多
     */

    void loadmore();
}
