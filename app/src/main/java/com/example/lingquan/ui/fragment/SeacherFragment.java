package com.example.lingquan.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseFragment;
import com.example.lingquan.coustom.TextFlowLayout;
import com.example.lingquan.model.domain.History;
import com.example.lingquan.model.domain.LinaItemInfo;
import com.example.lingquan.model.domain.SeacherResult;
import com.example.lingquan.model.domain.Searchrecommend;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.presenter.IseahcerPresenter;
import com.example.lingquan.ui.activity.KouLingActivity;
import com.example.lingquan.ui.adapter.HomeAndSearchAdapter;
import com.example.lingquan.utils.KeyBordUtils;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.SizeUtils;
import com.example.lingquan.utils.ToastUtitls;
import com.example.lingquan.view.IseacherCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by RenChao on 2020/5/8.
 */
public class SeacherFragment extends BaseFragment implements IseacherCallBack, HomeAndSearchAdapter.onItemclickListon, TextFlowLayout.onFlowItemListion {
    private static final String TAG = "SeacherFragment";
    @BindView(R.id.search_hisotory_flow)
    public TextFlowLayout mhisotoryLayout;

    @BindView(R.id.search_recommend_flow)
    public TextFlowLayout mrecommendLayout;


    @BindView(R.id.search_history_view)
    public LinearLayout history_view;

    @BindView(R.id.search_recommend_view)
    public LinearLayout recommend_view;

    @BindView(R.id.delete_img)
    public ImageView delete_keywords;

    @BindView(R.id.search_recycler)
    public RecyclerView search_recycler;

    @BindView(R.id.search_refresh)
    public TwinklingRefreshLayout search_refresh;

    @BindView(R.id.seacher_cancel)
    public TextView seacher_cancel;
    @BindView(R.id.seacher_et)
    public EditText seacher_et;
    @BindView(R.id.input_delete)
    public ImageView input_deletw;

    private IseahcerPresenter mSeacherPersenter;
    //    private SeachResultAdapter mAdapter;
    HomeAndSearchAdapter mAdapter;
    private IkoulingPresenter mKoulingPersenter;

    @Override
    protected int getrootviewId() {
        return R.layout.seacher_fragment;
    }


    @Override
    protected View loadrootview(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    protected void initview(View view) {

        setstates(State.SUCCESS);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        search_recycler.setLayoutManager(linearLayoutManager);

        // mAdapter = new SeachResultAdapter();
        mAdapter = new HomeAndSearchAdapter();
        search_recycler.setAdapter(mAdapter);
        search_refresh.setEnableLoadmore(true);
        search_refresh.setEnableRefresh(false);
        search_refresh.setEnableOverScroll(true);

        search_recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(), 1.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 1.5f);
            }
        });
    }

    @Override
    protected void onRetryClick() {
        super.onRetryClick();
        if (mSeacherPersenter != null) {
            mSeacherPersenter.reseacher();
        }

    }

    @Override
    protected void initListison() {
        super.initListison();

        delete_keywords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeacherPersenter.deleteHistory();
            }
        });

        mAdapter.setonclickListion(this);

        search_refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mSeacherPersenter.loadmore();
            }
        });

        seacher_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && mSeacherPersenter != null) {
                    String keywords = v.getText().toString().trim();
                    Log.d(TAG, "onEditorAction: -----" + keywords);
                    if (TextUtils.isEmpty(keywords)) {
                        return false;
                    }

                 //   mSeacherPersenter.doseacher(keywords);
                    toseacher(keywords);
                }

                return false;
            }

        });
        //监听输入框变化

        seacher_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //显示删除图标
                if (hasinput(true)) {
                    input_deletw.setVisibility(View.VISIBLE);

                } else {
                    input_deletw.setVisibility(View.GONE);
                }
                if (hasinput(false)) {

                    seacher_cancel.setText("搜索");
                } else {
                    seacher_cancel.setText("取消");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        input_deletw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seacher_et.setText("");
                //会带历史记录界面
                switch2page();
            }
        });
        seacher_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasinput(false)) {
                    //发起搜索
                    if (mSeacherPersenter != null) {
                      //  mSeacherPersenter.doseacher(seacher_et.getText().toString().trim());
                        toseacher(seacher_et.getText().toString().trim());
                        KeyBordUtils.hide(getContext(), v);
                    }
                } else {
                    KeyBordUtils.hide(getContext(), v);
                }
            }
        });

        mrecommendLayout.setonItemListion(this);
        mhisotoryLayout.setonItemListion(this);

    }

    //切换到历史记录和推荐界面

    private void switch2page() {
        if (mSeacherPersenter != null) {
            mSeacherPersenter.getseacherHistory();
        }


        if (mrecommendLayout.getcontentsize()!=0) {
            recommend_view.setVisibility(View.VISIBLE);
        }else {
            recommend_view.setVisibility(View.GONE);
        }

        search_refresh.setVisibility(View.GONE);

    }

    public boolean hasinput(boolean flag) {
        if (flag) {

            return seacher_et.getText().toString().length() > 0;
        } else {
            return seacher_et.getText().toString().trim().length() > 0;
        }

    }

    @Override
    protected void release() {
        super.release();
        if (mSeacherPersenter != null) {
            mSeacherPersenter.unregiterViewCallback(this);
        }
    }

    @Override
    protected void initPersenter() {
        super.initPersenter();

        mSeacherPersenter = PersenterManager.getInstance().getSeacherPersenter();
        mSeacherPersenter.regiesterViewCallback(this);
        //获取推荐词
        mSeacherPersenter.getrecommendwords();

        mSeacherPersenter.getseacherHistory();

    }

    @Override
    public void onHistoryload(History result) {


        if (result == null || result.getHistoryes().size() == 0) {
            history_view.setVisibility(View.GONE);

        } else {

            history_view.setVisibility(View.VISIBLE);
            mhisotoryLayout.settextList(result.getHistoryes());

        }

    }

    @Override
    public void onHistoryDelete() {
        if (mSeacherPersenter != null) {
            mSeacherPersenter.getseacherHistory();
        }


    }

    @Override
    public void onseacherSuccess(SeacherResult seacherResult) {
        Log.d(TAG, "onseacherSuccess: " + seacherResult);
        setstates(State.SUCCESS);

        //隐藏搜索历史
        history_view.setVisibility(View.GONE);
        recommend_view.setVisibility(View.GONE);
        //显示列表
        search_refresh.setVisibility(View.VISIBLE);
        //设置数据

        try {
            mAdapter.setData(seacherResult.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data());

        } catch (Exception e) {
            e.printStackTrace();
            setstates(State.EMPTY);

        }


    }

    /**
     * 加载更多数据成功回显
     *
     * @param seacherResult
     */

    @Override
    public void onmoreload(SeacherResult seacherResult) {
        search_refresh.finishLoadmore();
        List<SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> map_data
                = seacherResult.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data();
        mAdapter.addData(map_data);


    }

    @Override
    public void onmoreloadempty() {
        ToastUtitls.showToast("没有更多数据");

    }

    @Override
    public void onmoreloaderror() {
        ToastUtitls.showToast("网络错误，请稍后重试");

    }

    @Override
    public void onrecommendwordsload(List<Searchrecommend.DataBean> recommendwords) {
        Log.d(TAG, "onrecommendwordsload: ----" + recommendwords.size());
        List<String> recommends = new ArrayList<>();
        for (Searchrecommend.DataBean item : recommendwords) {
            recommends.add(item.getKeyword());
        }

        if (recommendwords == null || recommendwords.size() == 0) {
            recommend_view.setVisibility(View.GONE);

        } else {
            mrecommendLayout.settextList(recommends);
            recommend_view.setVisibility(View.VISIBLE);


        }


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

    /**
     * 列表点击事件
     *
     * @param item
     */
//    @Override
//    public void itemclick(SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean item) {
//        Log.d(TAG, "itemclick: ------->");
//
//        handItenclick(item);
//    }
    @Override
    public void itemclick(LinaItemInfo item) {
        Log.d(TAG, "itemclick: ------->");

        handItenclick(item);
    }

    private void handItenclick(LinaItemInfo item) {


        String title = item.gettitle();
        String url = item.getclickurl();
        if (TextUtils.isEmpty(url)) {
            url = item.geturl();
        }
        String cover = item.geturl();


        mKoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
        mKoulingPersenter.getkouling(title, url, cover);

        startActivity(new Intent(getContext(), KouLingActivity.class));
    }

    //推荐和历史点击事件

    @Override
    public void flowitemclick(String txt) {
        toseacher(txt);

    }

    private void toseacher(String txt) {
        if (mSeacherPersenter != null) {
            seacher_et.setText(txt);
            seacher_et.setFocusable(true);
            seacher_et.setSelection(txt.length(),txt.length());
            //移动到顶部
            search_recycler.scrollToPosition(0);

            mSeacherPersenter.doseacher(txt);
        }
    }


//    private void handItenclick(SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean item) {
//
//
//        String title=item.getTitle();
//        String url=item.getCoupon_share_url();
//        if (TextUtils.isEmpty(url)) {
//            url = item.getPict_url();
//        }
//        String cover=item.getPict_url();
//
//
//        mKoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
//        mKoulingPersenter.getkouling(title,url,cover);
//
//        startActivity(new Intent(getContext(), KouLingActivity.class));
//    }
}
