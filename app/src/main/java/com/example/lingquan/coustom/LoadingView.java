package com.example.lingquan.coustom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.lingquan.R;

/**
 * Created by RenChao on 2020/5/15.
 */
public class LoadingView extends AppCompatImageView {
    private float degrees=0;
    private boolean mNeedRotate=true;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setImageResource(R.mipmap.loadimg);


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate=true;
        startRotate();

    }

    //开始旋转
    private void startRotate() {

        post(new Runnable() {
            @Override
            public void run() {
                degrees+=10;
                if(degrees==360){
                    degrees=0;
                }
                invalidate();
                if(getVisibility()!=VISIBLE&& !mNeedRotate){
                    removeCallbacks(this);
                }else{

                    postDelayed(this, 50);
                }

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mNeedRotate=false;

    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.rotate(degrees, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
