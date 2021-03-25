package com.example.lingquan.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lingquan.R;
import com.example.lingquan.model.domain.LinaItemInfo;
import com.example.lingquan.utils.URLutil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RenChao on 2020/5/22.
 */
public class HomeAndSearchAdapter extends RecyclerView.Adapter<HomeAndSearchAdapter.Myhodle> {


    List<LinaItemInfo> mdata = new ArrayList<>();
    private onItemclickListon mItemonclickListion;

    @NonNull
    @Override
    public HomeAndSearchAdapter.Myhodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        ButterKnife.bind(this, view);
        return new HomeAndSearchAdapter.Myhodle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAndSearchAdapter.Myhodle holder, int position) {
        LinaItemInfo mapDataBean = mdata.get(position);

        holder.setitemdata(mapDataBean);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemonclickListion != null) {

                    mItemonclickListion.itemclick(mapDataBean);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void setData(List<? extends LinaItemInfo> linadata) {

        this.mdata.clear();
        this.mdata.addAll(linadata);
        notifyDataSetChanged();
    }


    public void addData(List<? extends LinaItemInfo> contents) {
        //添加之前拿到原来的size
        int olderSize = mdata.size();
        mdata.addAll(contents);
        //更新UI
        notifyItemRangeChanged(olderSize,contents.size());
    }




    public class Myhodle extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img_cover)
        public ImageView img;
        @BindView(R.id.item_title)
        public TextView txt1;
        @BindView(R.id.item_price)
        public TextView txt2;
        @BindView(R.id.item_priceed)
        public TextView txt3;
        @BindView(R.id.item_totle_pepole)
        public TextView txt4;
        @BindView(R.id.item_sheng)
        public TextView txt5;


        public Myhodle(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setitemdata(LinaItemInfo mapDataBean) {
//            ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
//            int width = layoutParams.width;
//            int height = layoutParams.height;
//
//            int coversize = (width > height ? width : height) / 2;


            String final_price = mapDataBean.getfinalprice();
            long coupon_amount = mapDataBean.getcoupouamont();
            float resultprice = Float.parseFloat(final_price) - coupon_amount;
            txt1.setText(mapDataBean.gettitle());
            //占位符模板R.string.sheng_price
            txt5.setText(String.format(itemView.getContext().getString(R.string.sheng_price), coupon_amount));
            //中划线
            txt3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            txt3.setText(String.format(itemView.getContext().getString(R.string.yuan_price), final_price));

            //保留两位小数
            txt2.setText(String.format("%.2f", resultprice));
            txt4.setText(String.format(itemView.getContext().getString(R.string.sale_pepol), mapDataBean.getvolume()));
            //图片加载框架
            String coverurl = URLutil.getcoverurl(mapDataBean.geturl());
//            Log.d(TAG, "coverurl: -->"+coverurl);
            Glide.with(itemView.getContext()).load(coverurl).into(img);
        }
    }

    public void setonclickListion(onItemclickListon liston) {
        this.mItemonclickListion = liston;
    }

    public interface onItemclickListon {
        void itemclick(LinaItemInfo mapDataBean);
    }
}

