package com.h.myapplication.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.h.myapplication.base.BaseFragment;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragments;
    String[] mTabTittle;

    public TabFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments,String[] tabTittle) {
        super(fm);
        this.mFragments = fragments;
        this.mTabTittle = tabTittle;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mFragments.size();//有几个页面
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTittle[position];
    }
}
