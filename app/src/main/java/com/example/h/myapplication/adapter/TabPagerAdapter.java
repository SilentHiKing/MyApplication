package com.example.h.myapplication.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.example.h.myapplication.base.BaseFragment;
import com.example.h.myapplication.base.BasePageAdapter;

import java.util.List;

public class TabPagerAdapter extends BasePageAdapter {

    List<BaseFragment> mFragments;

    public TabPagerAdapter(List<BaseFragment> fragments){
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        BaseFragment fragment = mFragments.get(position);

//        return super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
