package com.example.lingquan.ui.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.lingquan.R;
import com.example.lingquan.model.domain.SellShop;
import com.example.lingquan.utils.URLutil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RenChao on 2020/5/20.
 */
public class OnsellShopAdapter extends RecyclerView.Adapter<OnsellShopAdapter.myhodler> {

    private static final String TAG = "OnsellShopAdapter";
    List<SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mDataBeans = new ArrayList<>();
    private itemclickListion mitemlistion=null;

    @NonNull
    @Override
    public OnsellShopAdapter.myhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell_shop, parent, false);

        return new myhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myhodler holder, int position) {
        SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data = mDataBeans.get(position);
        holder.setitemdata(data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mitemlistion != null) {
                    mitemlistion.clickitem(data);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataBeans.size();
    }

    public void setData(SellShop s) {
        this.mDataBeans.clear();
        this.mDataBeans.addAll(s.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data());
        notifyDataSetChanged();



    }
    //更多数据显示

    public void setMoreLoadconten(SellShop s) {
        List<SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> map_data = s.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        int size=this.mDataBeans.size();
        this.mDataBeans.addAll(map_data);
        notifyItemRangeChanged(size-1,map_data.size());
    }


    public class myhodler extends ViewHolder {
        @BindView(R.id.sell_img)
        public ImageView img;
        @BindView(R.id.sell_title)
        public TextView txt_title;
        @BindView(R.id.sell_price)
        public TextView txt_price;
        @BindView(R.id.sell_quanhou)
        public TextView txt_quan;


        public myhodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }


        public void setitemdata(SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {


            String imgurl = URLutil.getcoverurl(data.getPict_url());

            Glide.with(itemView.getContext()).load(imgurl).into(img);

            txt_title.setText(data.getTitle());
            txt_price.setText("￥"+data.getZk_final_price());
            txt_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            int zheorice = data.getCoupon_amount();
            float finalorice=Float.parseFloat(data.getZk_final_price());
            float price=finalorice-zheorice;
            txt_quan.setText("券后价："+String.format("%.2f",price));
        }
    }


    public void setOnItemLition(itemclickListion listion){
        this.mitemlistion=listion;
    }


    public interface itemclickListion{
        void clickitem( SellShop.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data);
    }


}
