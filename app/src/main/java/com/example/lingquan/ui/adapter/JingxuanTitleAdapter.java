package com.example.lingquan.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lingquan.R;
import com.example.lingquan.model.domain.JingxuanBean;
import com.example.lingquan.model.domain.JingxuanContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RenChao on 2020/5/18.
 */
public class JingxuanTitleAdapter extends RecyclerView.Adapter<JingxuanTitleAdapter.Myholder> {

    private List<JingxuanBean.DataBean> mdata = new ArrayList<>();
    private int cruurtselectPosition = 0;
    private onTitleClickListion mTitleItemclick = null;

    @NonNull
    @Override
    public JingxuanTitleAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jx_fragment_recyclertitle_item, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JingxuanTitleAdapter.Myholder holder, int position) {
        JingxuanBean.DataBean dataBean = mdata.get(position);
        TextView txt = holder.itemView.findViewById(R.id.jx_recyclertitle_item);
        txt.setText(dataBean.getFavorites_title());
        if (cruurtselectPosition == position) {
            txt.setBackgroundColor(Color.parseColor("#EFEFEF"));
        } else {
            txt.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleItemclick != null ) {
                    //修改当前的位置
                    if (cruurtselectPosition != position) {
                        cruurtselectPosition=position;
                        mTitleItemclick.clickItem(dataBean);
                        notifyDataSetChanged();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void setData(JingxuanBean jingxuanBean) {
        List<JingxuanBean.DataBean> data = jingxuanBean.getData();

        if (data != null) {
            mdata.clear();
            this.mdata.addAll(data);
            notifyDataSetChanged();

        }
//        if (mdata.size()>0) {
//            mTitleItemclick.clickItem(mdata.get(cruurtselectPosition));
//        }
    }



    public class Myholder extends RecyclerView.ViewHolder {


        public Myholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setonitemClickListion(onTitleClickListion listion) {
        this.mTitleItemclick = listion;

    }

    public interface onTitleClickListion {
        void clickItem(JingxuanBean.DataBean jx);
    }
}
