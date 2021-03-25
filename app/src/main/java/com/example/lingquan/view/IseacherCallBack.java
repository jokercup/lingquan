package com.example.lingquan.view;

import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.model.domain.History;
import com.example.lingquan.model.domain.SeacherResult;
import com.example.lingquan.model.domain.Searchrecommend;

import java.util.List;

/**
 * Created by RenChao on 2020/5/21.
 */
public interface IseacherCallBack extends IBaseCallback {

    /**
     * 获取历史数据
     */
    void onHistoryload(History result);


    /**
     * 删除历史数据
     */


    void onHistoryDelete();

    /**
     * 搜索成功结果
     * @param seacherResult
     */

    void onseacherSuccess(SeacherResult seacherResult);


    /**
     * 获取更多
     */
    void onmoreload(SeacherResult seacherResult);

    /**
     * 没有更多内容
     */

    void onmoreloadempty();
    /**
     * 加载更多网络出错
     */
    void onmoreloaderror();


    /**
     * 推荐词获取结果
     * @param recommendwords
     */
    void onrecommendwordsload(List<Searchrecommend.DataBean> recommendwords);

}
