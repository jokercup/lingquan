package com.example.lingquan.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lingquan.model.domain.Categoryies;
import com.example.lingquan.ui.fragment.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RenChao on 2020/5/9.
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    List<Categoryies.DataBean> catelist = new ArrayList<>();

    public HomePageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return catelist.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        HomePageFragment homePageFragment=HomePageFragment.newInstance(catelist.get(position));
        Categoryies.DataBean dataBean = catelist.get(position);


        return homePageFragment;
    }

    @Override
    public int getCount() {
        return catelist.size();
    }

    public void setcategorydata(Categoryies categoryies) {
        catelist.clear();
        List<Categoryies.DataBean> data = categoryies.getData();
        catelist.addAll(data);
        notifyDataSetChanged();
    }
}
