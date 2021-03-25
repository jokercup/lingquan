package com.example.lingquan.view;

import com.example.lingquan.base.IBaseCallback;
import com.example.lingquan.model.domain.PageCategory;

import java.util.List;

/**
 * Created by RenChao on 2020/5/11.
 */
public interface ICategroPageCallback extends IBaseCallback {


    /**
     * 数据加载回来
     * @param calist
     */
   void  ongetContentload(List<PageCategory.DataBean> calist);


    /**
     * 加载更多网络错误

     */
   void onloadmoreError();
    /**
     * 加载更多无数据

     */
   void onloadmoreEmpty();
    /**
     * 加载更多内容
     * @param contens
     */
   void onloadmoreload(List<PageCategory.DataBean> contens);
    /**
     * 加载轮播图
     * @param contens
     */
   void onbannerload(List<PageCategory.DataBean> contens);


   int getmateriaID();

}
