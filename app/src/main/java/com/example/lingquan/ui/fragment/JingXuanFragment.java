package com.example.lingquan.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseFragment;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.model.domain.JingxuanContent;
import com.example.lingquan.presenter.IjingxuanPagePersenter;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.ui.activity.KouLingActivity;
import com.example.lingquan.ui.adapter.JingxuanTitleAdapter;
import com.example.lingquan.ui.adapter.JingxuanshopAdapter;
import com.example.lingquan.utils.Constans;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.SizeUtils;
import com.example.lingquan.view.IjingxuanCallBack;

import java.util.List;

import butterknife.BindView;

/**
 * Created by RenChao on 2020/5/8.
 */
public class JingXuanFragment extends BaseFragment implements IjingxuanCallBack, JingxuanTitleAdapter.onTitleClickListion, JingxuanshopAdapter.OnItemLingquanclickListion {
    private static final String TAG = "JingXuanFragment";

    private IjingxuanPagePersenter mPersenter;
    @BindView(R.id.jx_recycler_title)
    public RecyclerView mRecyclerView_title;
    @BindView(R.id.jx_recycler_shop)
    public RecyclerView mRecyclerView_shop;
    private JingxuanTitleAdapter mTitleAdapter;
    private JingxuanshopAdapter mShopAdapter;
    private IkoulingPresenter mKoulingPersenter;
    @BindView(R.id.base_bar_title)
    public TextView base_bar_title;
    @Override
    protected int getrootviewId() {
        return R.layout.jingxuan_fragment;
    }

    @Override
    protected void initview(View view) {
        setstates(State.SUCCESS);
        base_bar_title.setText("精选宝贝");
        //标题recyclerview
        mRecyclerView_title.setLayoutManager(new LinearLayoutManager(getContext()));
        mTitleAdapter = new JingxuanTitleAdapter();
        mRecyclerView_title.setAdapter(mTitleAdapter);
        //商品recyclerview
        mRecyclerView_shop.setLayoutManager(new LinearLayoutManager(getContext()));
        mShopAdapter = new JingxuanshopAdapter();
        mRecyclerView_shop.setAdapter(mShopAdapter);
        mRecyclerView_shop.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int topANDbottom = SizeUtils.dip2px(getContext(), 4);
                int leftANDright = SizeUtils.dip2px(getContext(), 6);
                outRect.top=topANDbottom;
                outRect.bottom=topANDbottom;
                outRect.left=leftANDright;
                outRect.right=leftANDright;
            }
        });
    }

    @Override
    protected void initPersenter() {

        mPersenter = PersenterManager.getInstance().getJingXuanPagePersent();
        mPersenter.regiesterViewCallback(this);
        //获取数据
        mPersenter.getcategoryies();
    }

    @Override
    protected void release() {
        super.release();
        if (mPersenter != null) {
            mPersenter.unregiterViewCallback(this);

        }
    }
    @Override
    protected void onRetryClick() {
        //网络错误,点击了重试
        //重新加载分类内容
        if (mPersenter != null) {
            mPersenter.reloadContent();
        }
    }


    @Override
    protected void initListison() {
        super.initListison();
        mTitleAdapter.setonitemClickListion(this);
        mShopAdapter.setonlingquanListion(this);
    }

    @Override
    public void onCategoryiesLoad(JingxuanBean jingxuanBean) {
        setstates(State.SUCCESS);
        //分页数据
        Log.d(TAG, "onCategoryiesLoad: --->" + jingxuanBean.toString());
        mTitleAdapter.setData(jingxuanBean);
        //拿到分类title获取内容
        List<JingxuanBean.DataBean > dataBeans=jingxuanBean.getData();
        mPersenter.ongetcategoryCOnten(dataBeans.get(0));
    }

    @Override
    public void oncategoryContent(JingxuanContent jx) {
        Log.d(TAG, "oncategoryContent: --->" + jx.getData()
                .getTbk_uatm_favorites_item_get_response()
                .getResults().getUatm_tbk_item().get(0).getTitle());
        setstates(State.SUCCESS);
        mShopAdapter.setData(jx);
        // Item 移动到 可见 Item 的第一项
        mRecyclerView_shop.scrollToPosition(0);


    }

    @Override
    protected View loadrootview(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_title_bar,container,false);
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
    public void clickItem(JingxuanBean.DataBean jx) {
        mPersenter.ongetcategoryCOnten(jx);
        //分类点击事件
        Log.d(TAG, "clickItem: ---->"+jx.getFavorites_title());

        
    }

    @Override
    public void lingquanclick(JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data) {
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
}
