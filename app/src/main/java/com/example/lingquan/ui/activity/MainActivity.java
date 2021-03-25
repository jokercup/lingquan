package com.example.lingquan.ui.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;

import com.example.lingquan.R;
import com.example.lingquan.base.BaseActivity;
import com.example.lingquan.base.BaseFragment;
import com.example.lingquan.ui.fragment.HomeFragment;
import com.example.lingquan.ui.fragment.JingXuanFragment;
import com.example.lingquan.ui.fragment.SellShopFragment;
import com.example.lingquan.ui.fragment.SeacherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainActivity{


    @BindView(R.id.but_nav_view)
    public BottomNavigationView btnNav;
    private static final String TAG = "MainActivity";
    private HomeFragment mHf;
    private JingXuanFragment mJf;
    private SellShopFragment mRf;
    private SeacherFragment mSf;
    private FragmentManager mFm;
    private FragmentTransaction mFt;



    private void initfragmen() {
        mHf = new HomeFragment();
        mJf = new JingXuanFragment();
        mRf = new SellShopFragment();
        mSf = new SeacherFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHf);
    }


    public void initListener(){

       btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Log.d(TAG,item.getTitle().toString());
               if(item.getItemId()==R.id.navigation_homefirst){
                   switchFragment(mHf);
               }else if(item.getItemId()==R.id.navigation_homesecond){
                   switchFragment(mJf);
               }else if(item.getItemId()==R.id.navigation_homethird){
                   switchFragment(mRf);
               }else if(item.getItemId()==R.id.navigation_homefourth){
                   switchFragment(mSf);
               }
               return true;



           }
       });

    }

    /**
     * 上一次显示的fragment
     * @param tag
     */
    private BaseFragment lastfragment=null;


    private void switchFragment(BaseFragment tag) {
        mFt = mFm.beginTransaction();

        //如果存在就隐藏
        if (lastfragment != null) {
            mFt.hide(lastfragment);
        }
        //如果不存在就添加
        if (!tag.isAdded()) {
            mFt.add(R.id.page_fragment,tag);
        }else{
            //存在就显示
            mFt.show(tag);

        }
        //替换
        lastfragment=tag;
//        mFt.replace(R.id.page_fragment, tag);
        mFt.commit();
    }

    @Override
    protected void initview() {
        initfragmen();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPersenter() {

    }

    @Override
    protected void initEvent() {

        initListener();
    }

    /**
     * 跳转到搜索界面
     */
    public void switch2Search() {
        // switchFragment(mSearchFragment);
        //切换导航栏的选中项
        btnNav.setSelectedItemId(R.id.navigation_homefourth);
    }
}
