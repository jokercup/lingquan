package com.example.lingquan.base;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lingquan.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RenChao on 2020/5/14.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //黑白app

//        View decorView = this.getWindow().getDecorView();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        Paint paint = new Paint();
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        decorView.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);


        mBind = ButterKnife.bind(this);
        initview();
        initEvent();
        initPersenter();
    }

    protected abstract void initPersenter();


    /**
     * 需要就复写
     *
     */

    protected void initEvent() {

    }

    protected abstract void initview();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind!=null) {
            mBind.unbind();
        }
        this.release();
    }

    protected void release() {

    }


}
