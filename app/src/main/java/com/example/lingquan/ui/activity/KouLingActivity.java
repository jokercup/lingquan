package com.example.lingquan.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lingquan.R;
import com.example.lingquan.base.BaseActivity;
import com.example.lingquan.coustom.LoadingView;
import com.example.lingquan.model.domain.KoulingResult;
import com.example.lingquan.presenter.IkoulingPresenter;
import com.example.lingquan.presenter.impl.KoulingPersenterImpl;
import com.example.lingquan.utils.PersenterManager;
import com.example.lingquan.utils.ToastUtitls;
import com.example.lingquan.utils.URLutil;
import com.example.lingquan.view.IkoulingCallBack;

import butterknife.BindView;

public class KouLingActivity extends BaseActivity implements IkoulingCallBack {
    private static final String TAG = "KouLingActivity";
    private IkoulingPresenter mkoulingPersenter;
    @BindView(R.id.kouling_backimg)
    public ImageView backimg;
    @BindView(R.id.kouling_img)
    public ImageView koulingImg;
    @BindView(R.id.kouling_txt)
    public EditText koulingedtxt;
    @BindView(R.id.kouling_btn)
    public Button koulingBtn;

    @BindView(R.id.loading_tishi)
    public TextView loading_tishi;

    @BindView(R.id.cover_loading)
    public View cover_loading;

    //判断是否有淘宝
    boolean installed =false;

    @Override
    protected void initPersenter() {
         mkoulingPersenter = PersenterManager.getInstance().getKoulingPersenter();
        if (mkoulingPersenter != null) {
            mkoulingPersenter.regiesterViewCallback(this);
        }


        //判断是否安装有taobao
        PackageManager pm = getPackageManager();

        try{

            pm.getPackageInfo("com.taobao.taobao",PackageManager.GET_ACTIVITIES);

            installed =true;

        }catch(PackageManager.NameNotFoundException e){

            installed =false;

        }

        Log.d(TAG, "initPersenter: ----->"+installed);

        koulingBtn.setText(installed?"打开淘宝领券":"复制淘口令");


}


    @Override
    protected void release() {

        if (mkoulingPersenter != null) {
            mkoulingPersenter.unregiterViewCallback(this);
        }
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initEvent() {
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        koulingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = koulingedtxt.getText().toString().trim();

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("kouling", trim);
                cm.setPrimaryClip(cd);

                if (installed) {
                    Intent taobaointent = new Intent();
//                    taobaointent.setAction("android.intent.action.MAIN");
//                    taobaointent.addCategory("android.intent.category.LAUNCHER");
                    ComponentName componentName = new ComponentName("com.taobao.taobao","com.taobao.tao.TBMainActivity");
                    taobaointent.setComponent(componentName);
                    startActivity(taobaointent);

                }else{
                    ToastUtitls.showToast("复制成功，去粘贴分享吧");
                }
            }
        });



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kou_ling;
    }

    @Override
    public void onKoulingLoad(String cover, KoulingResult kl) {
        hideLloading();
        Log.d(TAG, "onKoulingLoad:----> "+cover);
        if (!TextUtils.isEmpty(cover) &&koulingImg!=null) {
//            ViewGroup.LayoutParams layoutParams=koulingImg.getLayoutParams();
//            int width=layoutParams.width;
//            int size=width/2;
            String getcoverurl = URLutil.getcoverurl(cover);

            Glide.with(this).load(getcoverurl).into(koulingImg);
        }

        if (TextUtils.isEmpty(cover)) {
            koulingImg.setImageResource(R.mipmap.noimg);
        }
        if(kl!=null &&kl.getData().getTbk_tpwd_create_response()!=null){
            koulingedtxt.setText(kl.getData().getTbk_tpwd_create_response().getData().getModel());
        }
        if (cover_loading != null) {
            cover_loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoading() {
        showloading();
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onError() {
        hideLloading();
    }

    public void showloading(){
        if (cover_loading != null) {
            cover_loading.setVisibility(View.VISIBLE);

        }
        if (loading_tishi != null) {

            loading_tishi.setVisibility(View.GONE);
        }
    }


    public void hideLloading(){
        if (cover_loading != null) {
            cover_loading.setVisibility(View.GONE);
        }
        if (loading_tishi != null) {
            loading_tishi.setVisibility(View.VISIBLE);
        }
    }
}
