package com.example.lingquan.view;

import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.model.domain.JingxuanContent;

/**
 * Created by RenChao on 2020/5/18.
 */
public interface IjingxuanCallBack extends IBaseCallback {

    /**
     * 分页内容结果
     * @param jingxuanBean
     */
    void onCategoryiesLoad(JingxuanBean jingxuanBean);


    /**
     * 内容
     * @param jx
     */

    void oncategoryContent(JingxuanContent jx);
}
