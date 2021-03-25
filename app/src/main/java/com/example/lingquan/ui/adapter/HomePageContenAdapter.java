package com.example.lingquan.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lingquan.R;
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.ui.fragment.HomePageFragment;
import com.example.lingquan.utils.URLutil;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RenChao on 2020/5/11.
 */
public class HomePageContenAdapter extends RecyclerView.Adapter<HomePageContenAdapter.Myholder> {
    List<PageCategory.DataBean> mDate = new ArrayList<>();
    private static final String TAG = "HomePageContenAdapter";
    private onItemclickListon mItemonclickListion=null;

    @NonNull
    @Override
    public HomePageContenAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        ButterKnife.bind(this, view);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageContenAdapter.Myholder holder, int position) {
        //设置数据
        PageCategory.DataBean pageCategory = mDate.get(position);

        holder.setData(pageCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemonclickListion!=null) {

                    mItemonclickListion.itemclick(pageCategory);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public void setData(List<PageCategory.DataBean> calist) {
        mDate.clear();
        mDate.addAll(calist);
        notifyDataSetChanged();
    }

    public void addData(List<PageCategory.DataBean> contens) {
        int oldszie = mDate.size() - 1;
        mDate.addAll(contens);
        notifyItemRangeChanged(oldszie, contens.size());

    }


    public class Myholder extends RecyclerView.ViewHolder {
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


        public Myholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("StringFormatInvalid")
        public void setData(PageCategory.DataBean dataBean) {
            ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
            int width = layoutParams.width;
            int height = layoutParams.height;

            int coversize = (width > height ? width : height) / 2;


            String final_price = dataBean.getZk_final_price();
            long coupon_amount = dataBean.getCoupon_amount();
            float resultprice = Float.parseFloat(final_price) - coupon_amount;
            txt1.setText(dataBean.getTitle());
            //占位符模板R.string.sheng_price
            txt5.setText(String.format(itemView.getContext().getString(R.string.sheng_price), coupon_amount));
            //中划线
            txt3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            txt3.setText(String.format(itemView.getContext().getString(R.string.yuan_price), final_price));

            //保留两位小数
            txt2.setText(String.format("%.2f", resultprice));
            txt4.setText(String.format(itemView.getContext().getString(R.string.sale_pepol), dataBean.getVolume()));
            //图片加载框架
            String coverurl = URLutil.getcoverurl(dataBean.getPict_url(), coversize);
//            Log.d(TAG, "coverurl: -->"+coverurl);
            Glide.with(itemView.getContext()).load(coverurl).into(img);

        }

    }


    public void setonclickListion(onItemclickListon liston){
        this.mItemonclickListion=liston;
    }
    public interface onItemclickListon{
        void itemclick(PageCategory.DataBean item);
    }
}
