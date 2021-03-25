package com.example.lingquan.model;

import com.example.lingquan.model.domain.Categoryies;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.model.domain.JingxuanContent;
import com.example.lingquan.model.domain.KoulingParms;
import com.example.lingquan.model.domain.KoulingResult;
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.model.domain.SeacherResult;
import com.example.lingquan.model.domain.Searchrecommend;
import com.example.lingquan.model.domain.SellShop;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by RenChao on 2020/5/9.
 */
public interface Api {
    /**
     * 获取分页分类
     *
     * @return
     */

    @GET("discovery/categories")
    Call<Categoryies> getcategory();

    /**
     * 获取分页内容
     *
     * @param url
     * @return
     */

    @GET
    Call<PageCategory> getPagecategroyContent(@Url String url);

    /**
     * 获取淘口令
     *
     * @param parms
     * @return
     */

    @POST("tpwd")
    Call<KoulingResult> getkoulingconten(@Body KoulingParms parms);

    /**
     * \
     * 精选分类结果
     *
     * @return
     */

    @GET("recommend/categories")
    Call<JingxuanBean> getjingxuanInfo();

    /**
     * 精选分类内容
     *
     * @return
     */
    @GET()
    Call<JingxuanContent> getjingxuanContent(@Url String id);

    /**
     * 获取特惠内容
     *
     * @param url
     * @return
     */
    @GET
    Call<SellShop> getsellshop(@Url String url);

    /**
     * 获取推荐词
     * @return
     */

    @GET("search/recommend")
    Call<Searchrecommend> getrecommendword();

    @GET("search")
    Call<SeacherResult> getseacher(@Query("page") int page,@Query("keyword") String keyword);

}
