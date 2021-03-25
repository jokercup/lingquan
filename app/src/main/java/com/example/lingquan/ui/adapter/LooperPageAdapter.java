package com.example.lingquan.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.ui.fragment.HomePageFragment;
import com.example.lingquan.utils.URLutil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RenChao on 2020/5/12.
 */
public class LooperPageAdapter extends PagerAdapter {

    private List<PageCategory.DataBean> mDataBeans = new ArrayList<>();
    private LooperItenOnclickListion mLooperClickListion=null;

    @Override
    public int getCount() {

        return Integer.MAX_VALUE;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int pointposition = position % 5;

        //获取图片宽高
        int measuredHeight = container.getMeasuredHeight();
        int measuredWidth = container.getMeasuredWidth();
        //计算高度大小
        int coversize = measuredHeight > measuredWidth ? measuredHeight : measuredWidth / 2;

        PageCategory.DataBean pageCategory = mDataBeans.get(pointposition);
        String coverurl = URLutil.getcoverurl(pageCategory.getPict_url(), coversize);

        ImageView iv = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(coverurl).into(iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLooperClickListion != null) {
                    mLooperClickListion.looperitemclick(pageCategory);
                }
            }
        });
        container.addView(iv);

        return iv;

    }

    public void setData(List<PageCategory.DataBean> contens) {
        mDataBeans.clear();
        mDataBeans.addAll(contens);
        notifyDataSetChanged();
    }


    public int getDataSize() {

        return mDataBeans.size();
    }

    public void setLooperClickListion(LooperItenOnclickListion listion){
        this.mLooperClickListion=listion;
    }



    public interface LooperItenOnclickListion{
       void looperitemclick(PageCategory.DataBean pa);

    }

}
