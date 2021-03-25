package com.example.lingquan.presenter;

import com.example.lingquan.base.BasePresenter;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.view.IjingxuanCallBack;

/**
 * Created by RenChao on 2020/5/18.
 */
public interface IjingxuanPagePersenter extends BasePresenter<IjingxuanCallBack> {

    /**
     * 获取分类
     */
    void getcategoryies();

    /**
     * 获取分页内容
     * @param jx
     */

    void ongetcategoryCOnten(JingxuanBean.DataBean jx);

    /**
     * 重新加载
     */
    void reloadContent();
}
