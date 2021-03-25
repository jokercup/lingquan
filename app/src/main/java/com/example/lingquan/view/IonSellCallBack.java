package com.example.lingquan.view;

import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.model.domain.SellShop;

/**
 * Created by RenChao on 2020/5/20.
 */
public interface IonSellCallBack extends IBaseCallback {

    /**
     * 数据加载成功
     * @param s
     */

    void onContentload(SellShop s);


    /**
     * 数据更多结果
     * @param s
     */
    void onMoreLoad(SellShop s);

    /**
     * 加载更多错误
     */
    void onmorloadError();

    /**
     * 没有更多
     */
    void onloadNoMore();
}
