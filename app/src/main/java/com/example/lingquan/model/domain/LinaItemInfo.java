package com.example.lingquan.model.domain;

/**
 * Created by RenChao on 2020/5/22.
 */
public interface LinaItemInfo  {

    /**
     * 获取标题
     */

    String  gettitle();

    /**
     * 获取连接
     */

    String  geturl();

    /**
     * 获取图片
     */

    String  getcover();

    /**
     * 获取原价
     */

    String  getfinalprice();

    /**
     * 获取销量
     */

    long  getvolume();

    /**
     * 获取优惠价格
     */

    long  getcoupouamont();
    /**
     * 获取口令
     */
    String getclickurl();
}
