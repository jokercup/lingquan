package com.example.lingquan.coustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lingquan.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RenChao on 2020/5/21.
 */
public class TextFlowLayout extends ViewGroup {

    private static final String TAG = "TextFlowLayout";
    private List<String> mlist = new ArrayList<>();

    private static final int DEFAULT_SPACE = 10;

    private float item_hspace = DEFAULT_SPACE;
    private float item_vspace = DEFAULT_SPACE;
    private int   mSlefwidth;
    private int mMeasuredHeight;
    private onFlowItemListion mitemlistion = null;

    public float getItem_hspace() {
        return item_hspace;
    }

    public void setItem_hspace(float item_hspace) {
        this.item_hspace = item_hspace;
    }

    public float getItem_vspace() {
        return item_vspace;
    }

    public void setItem_vspace(float item_vspace) {
        this.item_vspace = item_vspace;
    }

    public TextFlowLayout(Context context) {
        this(context, null);
    }

    public TextFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    //返回数据大小

    public int getcontentsize(){
        return mlist.size();
    }

    //所有行
    private List<List<View>> lines = new ArrayList<>();


    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray td = context.obtainStyledAttributes(attrs, R.styleable.Flowtextstyle);
        item_hspace = td.getDimension(R.styleable.Flowtextstyle_horizontalspace, DEFAULT_SPACE);
        item_vspace = td.getDimension(R.styleable.Flowtextstyle_verticalspace, DEFAULT_SPACE);
        //释放资源
        td.recycle();
    }

    /**
     * 布局
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放
//        View itemone = getChildAt(0);\
//        itemone.layout(0,0,itemone.getMeasuredWidth(),itemone.getMeasuredHeight());


        int topoffset = (int) item_hspace;
        for (List<View> views : lines) {
            //views是每一行
            int leftoffset = (int) item_hspace;
            for (View view : views) {
                view.layout(leftoffset, topoffset, leftoffset + view.getMeasuredWidth(), topoffset + view.getMeasuredHeight());
                leftoffset += view.getMeasuredWidth() + item_hspace;
            }
            topoffset += mMeasuredHeight + item_hspace;
        }

    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() == 0) {
            return;
        }
        List<View> line = null;
        lines.clear();

        mSlefwidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
      //  Log.d(TAG, "mSlefsize: " + mSlefwidth);
        //测量

        //测量孩子
        int child = getChildCount();
        for (int i = 0; i < child; i++) {
            View itemview = getChildAt(i);
            if (itemview.getVisibility() != VISIBLE) {
                continue;
            }
            //测量前
       //     Log.d(TAG, "onMeasure: " + itemview.getMeasuredHeight());

            measureChild(itemview, widthMeasureSpec, heightMeasureSpec);
            //测量后
      //      Log.d(TAG, "onMeasure: " + itemview.getMeasuredHeight());
            if (line == null) {
                //当前行未空，可以添加
                line = creatview(itemview);

            } else {
                //判断是否可以继续添加 
                if (canbeadd(itemview, line)) {
                    //可以添加
                    line.add(itemview);

                } else {
                    //新创建一行
                    line = creatview(itemview);

                }

            }
        }

        mMeasuredHeight = getChildAt(0).getMeasuredHeight();

        int selfheight = (int) (lines.size() * mMeasuredHeight + item_vspace * (lines.size() + 1) + 0.5f);
        //测量自己
        setMeasuredDimension(mSlefwidth, selfheight);

    }

    private List<View> creatview(View itemview) {
        List<View> line = new ArrayList<>();
        line.add(itemview);
        lines.add(line);

        return line;
    }

    /**
     * 判断当前行是否可以继续添加
     *
     * @param itemview
     * @param line
     */
    private boolean canbeadd(View itemview, List<View> line) {
        //如果小于等于当前控件的宽度，则可以添加
        int totalwidth = itemview.getMeasuredWidth();
        for (View view : line) {
            //叠加所有控件宽度
            totalwidth += view.getMeasuredWidth();
        }
        //水平间距宽度
        totalwidth += item_hspace * (line.size() + 1);
//        Log.d(TAG, "canbeadd: " + totalwidth);
//        Log.d(TAG, "canbeadd: " + mSlefwidth);
        //如果小于当前控件宽度则可以添加
        return totalwidth <= mSlefwidth;
    }


    public void settextList(List<String> strings) {
        removeAllViews();
        this.mlist.clear();
        this.mlist.addAll(strings);
        //list倒序
        Collections.reverse(mlist);
        for (String s : mlist) {
            //添加子view
            TextView item = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_view, this, false);
            item.setText(s);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mitemlistion != null) {
                        mitemlistion.flowitemclick(s);
                    }
                }
            });
            addView(item);

        }

    }

    public void setonItemListion(onFlowItemListion listion) {
        this.mitemlistion = listion;
    }


    public interface onFlowItemListion {
        void flowitemclick(String txt);
    }


}
