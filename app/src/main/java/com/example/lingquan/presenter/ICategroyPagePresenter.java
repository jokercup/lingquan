package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.view.ICategroPageCallback;

/**
 * Created by RenChao on 2020/5/11.
 */
public interface ICategroyPagePresenter extends BasePresenter<ICategroPageCallback> {
    /**
     * 根据ID获取内容
     *
     * @param categroyid
     */
    void getCartegroyID(int categroyid);

    /**
     * 加载更多
     */

    void loadmore(int categroyid);

    /**
     * 重新加载
     */
    void reload(int categroyid);



}
