package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.view.IseacherCallBack;

/**
 * Created by RenChao on 2020/5/21.
 */
public interface IseahcerPresenter extends BasePresenter<IseacherCallBack> {

    /**
     * 获取搜索历史
     */
    void getseacherHistory();

    /**
     * 删除搜索历史
     */
    void deleteHistory();


    /**
     * 搜索
     */
    void doseacher(String keywords);

    /**
     * 重新搜索
     */

    void reseacher();

    /**
     * 加载更多结果
     */
    void loadmore();

    /**
     * 获取热门关键词
     *
     */


    void getrecommendwords();
}
