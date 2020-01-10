package com.h.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.base.BasePageAdapter;

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
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        BaseFragment fragment = mFragments.get(position);

//        return super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
