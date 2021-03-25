package com.example.lingquan.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseFragment;

import com.example.lingquan.model.domain.Categoryies;
import com.example.lingquan.presenter.IhomePresenter;
import com.example.lingquan.presenter.impl.HomePresenterImpl;
import com.example.lingquan.ui.activity.IMainActivity;
import com.example.lingquan.ui.activity.ScanQRCodeActivity;
import com.example.lingquan.ui.adapter.HomePageAdapter;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.view.IhomeCallback;
import com.google.android.material.tabs.TabLayout;
import com.vondear.rxfeature.activity.ActivityScanerCode;

import butterknife.BindView;

/**
 * Created by RenChao on 2020/5/8.
 */
public class HomeFragment extends BaseFragment implements IhomeCallback {

    public IhomePresenter mIhomePresenter;
    @BindView(R.id.tab_title)
    public TabLayout mTabLayout;

    @BindView(R.id.scan_img)
    public ImageView scan_img;

    @BindView(R.id.homepage_seacher_box)
    public EditText seacher_box;


    @BindView(R.id.tab_view)
    public ViewPager mViewPager;
    private HomePageAdapter mHomePageAdapter;

    @Override
    protected int getrootviewId() {
        return R.layout.home_fragment;
    }


    @Override
    protected void loaddata() {
        //加载数据
        mIhomePresenter.getCategoies();
    }

    @Override
    protected View loadrootview(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);
    }
    @Override
    protected void initview(View view) {
        mTabLayout.setupWithViewPager(mViewPager);
        mHomePageAdapter = new HomePageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mHomePageAdapter);
    }

    @Override
    protected void initPersenter() {
        //创建persenter
        mIhomePresenter = PersenterManager.getInstance().getHomePresenter();
        mIhomePresenter.regiesterViewCallback(this);
        seacher_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索页面
                FragmentActivity activity = getActivity();
                if(activity instanceof IMainActivity) {
                    ((IMainActivity) activity).switch2Search();
                }
            }
        });
    }


    @Override
    protected void initListison() {
        super.initListison();
        scan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));

            }
        });
    }

    @Override
    public void onCategoires(Categoryies category) {
        setstates(State.SUCCESS);
        if (mHomePageAdapter != null) {
            //设置加载多少个页面数据
//            mViewPager.setOffscreenPageLimit(category.getData().size());
            mHomePageAdapter.setcategorydata(category);
        }

    }

    @Override
    protected void onRetryClick() {
        //网络错误,点击了重试
        //重新加载分类内容
        if(mIhomePresenter != null) {
            mIhomePresenter.getCategoies();
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

    @Override
    protected void release() {
        super.release();
        if (mIhomePresenter != null) {
            mIhomePresenter.unregiterViewCallback(this);
        }
    }
}
