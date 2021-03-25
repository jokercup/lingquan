package com.lcodecore.tkrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by RenChao on 2020/5/13.
 */
public class QuanNestedScrollView extends NestedScrollView {
    private int measureHight = 0;
    private int scrollHight = 0;
    private RecyclerView mRecyclerview;

    public QuanNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public QuanNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuanNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(target instanceof RecyclerView){
            this.mRecyclerview=(RecyclerView)target;

        }
        if (scrollHight < measureHight) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.scrollHight = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setHeadHight(int headHight) {
        this.measureHight = headHight;

    }

    public boolean isinbottom() {
        if (mRecyclerview!=null) {
            boolean b = !mRecyclerview.canScrollVertically(1);
            return b;
        }
        return false;
    }
}
