package com.example.lingquan.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseFragment;
import com.example.lingquan.model.domain.SellShop;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.presenter.IonSellpagePresenter;
import com.example.lingquan.ui.activity.KouLingActivity;
import com.example.lingquan.ui.adapter.OnsellShopAdapter;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.SizeUtils;
import com.example.lingquan.utils.ToastUtitls;
import com.example.lingquan.view.IonSellCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;

/**
 * Created by RenChao on 2020/5/8.
 */
public class SellShopFragment extends BaseFragment implements IonSellCallBack, OnsellShopAdapter.itemclickListion {
    private static final String TAG = "SellShopFragment";
    public static final int DEFAULT_SPAN=2;

    @BindView(R.id.sell_recycler)
    public RecyclerView sellrecycler;
    @BindView(R.id.sell_refresh)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.base_bar_title)
    public TextView base_bar_title;

    private IonSellpagePresenter mSellpagePresenter;
    private OnsellShopAdapter mAdapter;
    private IkoulingPresenter mKoulingPersenter;

    @Override
    protected int getrootviewId() {
        return R.layout.fragment_onsell;
    }

    @Override
    protected void initListison() {
        super.initListison();

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (mSellpagePresenter != null) {
                    mSellpagePresenter.loadmore();
                }
            }
        });
        mAdapter.setOnItemLition(this);

    }


    @Override
    protected View loadrootview(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_title_bar,container,false);
    }

    @Override
    protected void initPersenter() {
        super.initPersenter();
        mSellpagePresenter = PersenterManager.getInstance().getSellpagePresenter();
        mSellpagePresenter.regiesterViewCallback(this);
        mSellpagePresenter.getsellshopcontent();

    }

    @Override
    protected void initview(View view) {
        super.initview(view);
        base_bar_title.setText("特惠宝贝");
        setstates(State.SUCCESS);

        //设置适配器

        mAdapter = new OnsellShopAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),DEFAULT_SPAN);
        sellrecycler.setLayoutManager(layoutManager);
        sellrecycler.setAdapter(mAdapter);
        sellrecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int topTobottom = SizeUtils.dip2px(getContext(), 2.5f);
                int leftTOright = SizeUtils.dip2px(getContext(), 4f);
                outRect.top=topTobottom;
                outRect.bottom=topTobottom;
                outRect.left=leftTOright;
                outRect.right=leftTOright;
            }
        });


        mRefreshLayout.setEnableLoadmore(true);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableOverScroll(true);
    }

    @Override
    protected void release() {
        super.release();
        if (mSellpagePresenter != null) {
            mSellpagePresenter.unregiterViewCallback(this);
        }

    }

    @Override
    public void onContentload(SellShop s) {
        setstates(State.SUCCESS);
        Log.d(TAG, "onContentload: ----->"+s.getData().toString());

        mAdapter.setData(s);

    }

    @Override
    public void onMoreLoad(SellShop s) {

        //更多数据加载成功
        mRefreshLayout.finishLoadmore();

        mAdapter.setMoreLoadconten(s);

    }

    @Override
    public void onmorloadError() {
        mRefreshLayout.finishLoadmore();
        ToastUtitls.showToast("网络异常，请稍后重试");
    }

    @Override
    public void onloadNoMore() {
        mRefreshLayout.finishLoadmore();
        ToastUtitls.showToast("没有更多数据了");

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
        setstates(State.ERROR);
    }

    @Override
    public void clickitem(SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
        String title=data.getTitle();
        String url=data.getCoupon_click_url();
        if (TextUtils.isEmpty(url)) {
            url = data.getClick_url();
        }
        String cover=data.getPict_url();


        mKoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
        mKoulingPersenter.getkouling(title,url,cover);

        startActivity(new Intent(getContext(), KouLingActivity.class));
    }

    @Override
    protected void onRetryClick() {
        //网络错误,点击了重试
        //重新加载分类内容
        if(mSellpagePresenter != null) {
            mSellpagePresenter.reload();
        }
    }

}
