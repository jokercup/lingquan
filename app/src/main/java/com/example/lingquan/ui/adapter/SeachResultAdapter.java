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
import com.example.lingquan.model.domain.PageCategory;
import com.example.lingquan.model.domain.SeacherResult;
import com.example.lingquan.utils.URLutil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RenChao on 2020/5/22.
 */
public class SeachResultAdapter extends RecyclerView.Adapter<SeachResultAdapter.Myhodle> {

    List<SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> mdata = new ArrayList<>();
    private onItemclickListon mItemonclickListion;

    @NonNull
    @Override
    public Myhodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        ButterKnife.bind(this, view);
        return new SeachResultAdapter.Myhodle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myhodle holder, int position) {
        SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean mapDataBean = mdata.get(position);

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

    public void setData(SeacherResult seacherResult) {

        List<SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> map_data
                = seacherResult.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data();
        this.mdata.clear();
        this.mdata.addAll(map_data);
        notifyDataSetChanged();
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

        public void setitemdata(SeacherResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean mapDataBean) {
//            ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
//            int width = layoutParams.width;
//            int height = layoutParams.height;
//
//            int coversize = (width > height ? width : height) / 2;


            String final_price = mapDataBean.getZk_final_price();
            long coupon_amount =mapDataBean.getCoupon_amount() ;
            float resultprice = Float.parseFloat(final_price) - coupon_amount;
            txt1.setText(mapDataBean.getTitle());
            //占位符模板R.string.sheng_price
            txt5.setText(String.format(itemView.getContext().getString(R.string.sheng_price), coupon_amount));
            //中划线
            txt3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            txt3.setText(String.format(itemView.getContext().getString(R.string.yuan_price), final_price));

            //保留两位小数
            txt2.setText(String.format("%.2f", resultprice));
            txt4.setText(String.format(itemView.getContext().getString(R.string.sale_pepol), mapDataBean.getVolume()));
            //图片加载框架
            String coverurl = URLutil.getcoverurl(mapDataBean.getPict_url());
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
