package com.example.lingquan.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lingquan.R;
import com.example.lingquan.model.domain.JingxuanContent;
import com.example.lingquan.utils.Constans;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RenChao on 2020/5/18.
 */
public class JingxuanshopAdapter extends RecyclerView.Adapter<JingxuanshopAdapter.Myhodler> {
    List<JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean>  mdata
            = new ArrayList<>();
    private OnItemLingquanclickListion mlingquanlistion=null;

    @NonNull
    @Override
    public JingxuanshopAdapter.Myhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jx_fragment_recyclershop_item, parent, false);
        return new Myhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JingxuanshopAdapter.Myhodler holder, int position) {
        JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemBean = mdata.get(position);
        holder.setItemdata(itemBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlingquanlistion != null) {

                    mlingquanlistion.lingquanclick(itemBean);
                }

            }
        });
        
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public void setData(JingxuanContent jx) {
        if (jx.getCode() == Constans.CODE) {
            List<JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> shopdata = jx.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
            this.mdata.clear();
            this.mdata.addAll(shopdata);
            notifyDataSetChanged();

        }

    }

    public class Myhodler extends RecyclerView.ViewHolder {
        @BindView(R.id.jx_recyclershop_item_img)
        public ImageView img ;
        @BindView(R.id.jx_recyclershop_item_quan)
        public TextView quan ;
        @BindView(R.id.jx_recyclershop_item_title)
        public TextView title ;
        @BindView(R.id.jx_recyclershop_item_buy)
        public TextView buy ;
        @BindView(R.id.jx_recyclershop_item_price)
        public TextView price ;

        public Myhodler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemdata(JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean itemBean) {

            title.setText(itemBean.getTitle());
            String img_url = itemBean.getPict_url();
            Glide.with(itemView.getContext()).load(img_url).into(img);
            if (TextUtils.isEmpty(itemBean.getCoupon_click_url())) {
                price.setText("您来晚了，没有优惠券了");
                buy.setVisibility(View.GONE);
            }else{
                buy.setVisibility(View.VISIBLE);
//                price.setText("原价:"+itemBean.getZk_final_price());
                price.setText(   String.format(itemView.getContext().getString(R.string.jx_yuan_price),itemBean.getZk_final_price()));

            }

            if (TextUtils.isEmpty(itemBean.getCoupon_info())) {
                quan.setVisibility(View.GONE);

            }else{
                quan.setVisibility(View.VISIBLE);
                quan.setText(itemBean.getCoupon_info());
            }

        }
    }

    public void setonlingquanListion(OnItemLingquanclickListion listion){
        this.mlingquanlistion=listion;
    }

    public interface OnItemLingquanclickListion{
        void lingquanclick(JingxuanContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean data);
    }
}
