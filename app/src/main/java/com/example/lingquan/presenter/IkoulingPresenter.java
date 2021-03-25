package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.view.IkoulingCallBack;

/**
 * Created by RenChao on 2020/5/15.
 */
public interface IkoulingPresenter extends BasePresenter<IkoulingCallBack> {

    /**
     * 获取优惠券
     * @param title
     * @param url
     * @param cover
     */

    void getkouling(String title,String url,String cover);
}
