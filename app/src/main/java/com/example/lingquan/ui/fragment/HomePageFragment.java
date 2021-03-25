package com.example.lingquan.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseFragment;
import com.example.lingquan.coustom.AutoLooper;
import com.example.lingquan.model.domain.Categoryies;
import com.example.lingquan.model.domain.LinaItemInfo;
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.presenter.ICategroyPagePresenter;
import com.example.lingquan.presenter.IhomePresenter;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.ui.activity.KouLingActivity;
import com.example.lingquan.ui.adapter.HomeAndSearchAdapter;
import com.example.lingquan.ui.adapter.HomePageContenAdapter;
import com.example.lingquan.ui.adapter.LooperPageAdapter;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.SizeUtils;
import com.example.lingquan.utils.ToastUtitls;
import com.example.lingquan.view.ICategroPageCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.view.QuanNestedScrollView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by RenChao on 2020/5/9.
 */
public class HomePageFragment extends BaseFragment implements ICategroPageCallback, LooperPageAdapter.LooperItenOnclickListion, HomeAndSearchAdapter.onItemclickListon {
    public IhomePresenter mIhomePresenter;
    public static final String TAG = "HomePageFragment";
    private ICategroyPagePresenter mICategroyPage;
    private int mMaterialId;
    @BindView(R.id.page_recyclerview)
    public RecyclerView mRecyclerView;
//    private HomePageContenAdapter mHomePageContenAdapter;
    private HomeAndSearchAdapter mHomePageContenAdapter;
    @BindView(R.id.looper_banner)
    public AutoLooper looper_banner;
    private LooperPageAdapter mLooperPageAdapter;
    @BindView(R.id.tag_title)
    public TextView tag_title;
    @BindView(R.id.looper_point)
    public LinearLayout looper_point;
    private View mView;
    @BindView(R.id.home_page_refresh)
    public TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.home_page_parent)
    public LinearLayout homepageparent;
    @BindView(R.id.quanscrollview)
    public QuanNestedScrollView quanscrollview;
    @BindView(R.id.headview_layout)
    public LinearLayout headview_layout;
    private IkoulingPresenter mKoulingPersenter;


    @Override
    public void onResume() {
        super.onResume();
        looper_banner.startLooper();
    }

    @Override
    public void onPause() {
        super.onPause();
        looper_banner.stopLooper();
    }

    public static HomePageFragment newInstance(Categoryies.DataBean categoryies) {
        HomePageFragment homePageFragment = new HomePageFragment();

        Bundle mbundle = new Bundle();
        mbundle.putString("title", categoryies.getTitle());
        mbundle.putInt("materialId", categoryies.getId());
        homePageFragment.setArguments(mbundle);
        return homePageFragment;
    }

    ;

    @Override
    protected int getrootviewId() {
        return R.layout.home_page_fragment;
    }


    @Override
    protected void initview(View view) {

        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
        //创建适配器
//        mHomePageContenAdapter = new HomePageContenAdapter();
        mHomePageContenAdapter = new HomeAndSearchAdapter();
        //设置适配器
        mRecyclerView.setAdapter(mHomePageContenAdapter);

        //创建轮播图适配器
        mLooperPageAdapter = new LooperPageAdapter();
        looper_banner.setAdapter(mLooperPageAdapter);

        //设置加载更多
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(true);

    }

    @Override
    protected void initPersenter() {
        //创建persenter
        mICategroyPage = PersenterManager.getInstance().getICategroyPage();
        mICategroyPage.regiesterViewCallback(this);




    }

    @Override
    protected void loaddata() {
        Bundle mb = getArguments();
        String title = mb.getString("title");
        if (title != null) {
            tag_title.setText(title);
        }

        mMaterialId = mb.getInt("materialId");
        Log.d("HomePageFragment", "loaddata: -------" + title + "----" + mMaterialId);
        //加载数据
        if (mICategroyPage != null) {
            mICategroyPage.getCartegroyID(mMaterialId);
        }
    }


    @Override
    public void ongetContentload(List<PageCategory.DataBean> calist) {
        setstates(State.SUCCESS);
        mHomePageContenAdapter.setData(calist);


    }

    @Override
    public void onLoading() {
        setstates(State.LOADING);

    }

    @Override
    public void onEmpty() {
        setstates(State.EMPTY);
    }

    @Override
    public void onError() {
        //网络错误
        setstates(State.ERROR);

    }

    @Override
    public void onloadmoreError() {
        ToastUtitls.showToast("网络异常，请稍后重试");
        if (mRefreshLayout!=null) {
            mRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onloadmoreEmpty() {
        ToastUtitls.showToast("没有更多数据");
        if (mRefreshLayout!=null) {
            mRefreshLayout.finishLoadmore();
        }
     //   Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onloadmoreload(List<PageCategory.DataBean> contens) {

        mHomePageContenAdapter.addData(contens);
        if (mRefreshLayout!=null) {
            mRefreshLayout.finishLoadmore();
        }
        ToastUtitls.showToast("加载了"+contens.size()+"条数据");
//        Toast.makeText(getContext(), "加载了"+contens.size()+"条数据", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onbannerload(List<PageCategory.DataBean> contens) {
        Log.d(TAG, "onbannerload: -->" + contens);
        mLooperPageAdapter.setData(contens);
        //设置到中间点,设置第一个展示数据为第一个图片
        int dx = (Integer.MAX_VALUE / 2) % contens.size();
        dx = (Integer.MAX_VALUE / 2) - dx;
        looper_banner.setCurrentItem(dx);
        looper_banner.removeAllViews();
//        GradientDrawable selectdrawable = (GradientDrawable) getContext().getDrawable(R.drawable.shap_point);
//        GradientDrawable nomoredrawable = (GradientDrawable) getContext().getDrawable(R.drawable.shap_point);
//        nomoredrawable.setColor(getContext().getColor(R.color.white));


        for (int i = 0; i < contens.size(); i++) {
            mView = new View(getContext());
            int size = SizeUtils.dip2px(getContext(), 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(), 10);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(), 10);
            mView.setLayoutParams(layoutParams);
//            view.setBackgroundColor(getContext().getColor(R.color.white));
            if (i == 0) {
                mView.setBackgroundResource(R.drawable.shap_point);
            } else {
                mView.setBackgroundResource(R.drawable.shap_point_normal);
            }


            looper_point.addView(mView);
        }


        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                if (mICategroyPage!=null) {
                    mICategroyPage.loadmore(mMaterialId);

                }

            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
            }
        });
    }

    @Override
    public int getmateriaID() {

        return mMaterialId;
    }

    @Override
    protected void release() {
        if (mICategroyPage != null) {
            mICategroyPage.unregiterViewCallback(this);
        }

    }


    @Override
    protected void initListison() {
        //解决滑动冲突和界面复用

        homepageparent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (headview_layout==null) {
                    return;
                }
                int headhight=headview_layout.getMeasuredHeight();
                Log.d(TAG, "onGlobalLayout: ---->"+headhight);
                quanscrollview.setHeadHight(headhight);

                //获取内容高度设置打到recyclerview,数据复用界面，不重复创建
                int measuredHeight = homepageparent.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
                layoutParams.height=measuredHeight;
                mRecyclerView.setLayoutParams(layoutParams);
                //防止界面一直调用此方法，只有初始化布局时候调用
                if (measuredHeight!=0) {
                    homepageparent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });
        looper_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mLooperPageAdapter.getDataSize()==0) {
                    return;
                }
                int tarposition = position % mLooperPageAdapter.getDataSize();
          //      Log.d(TAG, "onPageSelected: " + tarposition);
                updataPosition(tarposition);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mHomePageContenAdapter.setonclickListion(this);
        mLooperPageAdapter.setLooperClickListion(this);


    }

    /**
     * 切换指示器
     *
     * @param tarposition
     */

    private void updataPosition(int tarposition) {

//        Log.d(TAG, "updataPosition: " + looper_point.getChildCount());

        for (int i = 0; i < looper_point.getChildCount(); i++) {

            View childAt = looper_point.getChildAt(i);
            if (i == tarposition) {
                childAt.setBackgroundResource(R.drawable.shap_point);
            } else {
                childAt.setBackgroundResource(R.drawable.shap_point_normal);
            }
        }

    }

    /**
     *  列表点击事件
     * @param item
     */

//    @Override
//    public void itemclick(LinaItemInfo item) {
//
//        Log.d(TAG, "itemclick: ------->");
//
//        handItenclick(item);
//
//    }

    private void handItenclick(PageCategory.DataBean item) {


        String title=item.getTitle();
        String url=item.getCoupon_click_url();
        if (TextUtils.isEmpty(url)) {
            url = item.getClick_url();
        }
        String cover=item.getPict_url();


        mKoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
        mKoulingPersenter.getkouling(title,url,cover);

        startActivity(new Intent(getContext(), KouLingActivity.class));
    }

    /**
     * 轮播点击事件
     * @param pa
     */

    @Override
    public void looperitemclick(PageCategory.DataBean pa) {

        handItenclick(pa);

    }


    @Override
    public void itemclick(LinaItemInfo item) {


        String title=item.gettitle();

        String url=item.getclickurl();
        if (TextUtils.isEmpty(url)) {
            url = item.geturl();
        }
        String cover=item.geturl();


        mKoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
        mKoulingPersenter.getkouling(title,url,cover);

        startActivity(new Intent(getContext(), KouLingActivity.class));

    }


}
