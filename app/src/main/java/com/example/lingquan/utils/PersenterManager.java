package com.example.lingquan.utils;

import android.util.Log;

import com.example.lingquan.presenter.ICategroyPagePresenter;
import com.example.lingquan.presenter.IhomePresenter;
import com.example.lingquan.presenter.IjingxuanPagePersenter;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.presenter.IonSellpagePresenter;
import com.example.lingquan.presenter.IseahcerPresenter;
import com.example.lingquan.presenter.impl.HomePresenterImpl;
import com.example.lingquan.presenter.impl.ICategroyPageImpl;
import com.example.lingquan.presenter.impl.JingXuanPagePersentImpl;
import com.example.lingquan.presenter.impl.KoulingPersenterImpl;
import com.example.lingquan.presenter.impl.SeacherPersenterImpl;
import com.example.lingquan.presenter.impl.SellShopPagePresenterImpl;

/**
 * Created by RenChao on 2020/5/15.
 */
public class PersenterManager {
    private static final String TAG = "PersenterManager";


    private static final  PersenterManager myPersentermanager=new PersenterManager();
    private final ICategroyPagePresenter mICategroyPage;
    private final IhomePresenter mHomePresenter;
    private final IkoulingPresenter mKoulingPersenter;
    private final IjingxuanPagePersenter mJingXuanPagePersent;
    private final IonSellpagePresenter mSellpagePresenter;

    private final IseahcerPresenter mSeacherPersenter;

    public IseahcerPresenter getSeacherPersenter() {
        return mSeacherPersenter;
    }

    public IonSellpagePresenter getSellpagePresenter() {
        return mSellpagePresenter;
    }

    public IjingxuanPagePersenter getJingXuanPagePersent() {

        return mJingXuanPagePersent;
    }

    public ICategroyPagePresenter getICategroyPage() {
        return mICategroyPage;
    }

    public IhomePresenter getHomePresenter() {
        return mHomePresenter;
    }

    public IkoulingPresenter getKoulingPersenter() {
        return mKoulingPersenter;
    }

    public static PersenterManager getInstance() {

        return myPersentermanager;
    }

    public PersenterManager() {

        mICategroyPage = new ICategroyPageImpl();
        mHomePresenter = new HomePresenterImpl();
        mKoulingPersenter = new KoulingPersenterImpl();
        mJingXuanPagePersent = new JingXuanPagePersentImpl();
        mSellpagePresenter = new SellShopPagePresenterImpl();
        mSeacherPersenter = new SeacherPersenterImpl();

    }
}
