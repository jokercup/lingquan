package com.example.lingquan.coustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.lingquan.R;
import com.example.lingquan.ui.adapter.LooperPageAdapter;

/**
 * 自动轮播
 * Created by RenChao on 2020/5/14.
 */
public class AutoLooper extends ViewPager {
    private static final String TAG = "AutoLooper";

    //自动轮播时间
    private static final long CRUUTTIME=3000;
    private long mduration=CRUUTTIME;

    public AutoLooper(@NonNull Context context) {
        this(context,null);
    }

    public AutoLooper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.AutoLooperstyle);
            mduration = t.getInteger(R.styleable.AutoLooperstyle_duration, (int)CRUUTTIME);
            Log.d(TAG, "init: ---->"+mduration);

            //回收资源
            t.recycle();

    }


    private boolean islooper=false;
    public void startLooper(){
        islooper=true;
        post(task);
    }

    private  Runnable task=new Runnable() {
        @Override
        public void run() {

            int item=getCurrentItem();
            item++;
            setCurrentItem(item);

            if(islooper){
                postDelayed(this,(int)mduration);
            }
        }
    };

    public void stopLooper(){
        islooper=false;
        removeCallbacks(task);
    }


    public void setDuration(int duration){
        this.mduration=duration;

    }
}
