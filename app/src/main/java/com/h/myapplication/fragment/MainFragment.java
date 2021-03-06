package com.h.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.h.myapplication.R;
import com.h.myapplication.adapter.TabFragmentPagerAdapter;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.skin.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MainFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    MyTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    String[] mTabTittle;
    List<BaseFragment> mFragments;
    TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        mViewPager.setAdapter(mTabFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Log.d("hello",System.currentTimeMillis()+"");
    }


    private void initData() {
        mTabTittle = getResources().getStringArray(R.array.tab_tittle);
        int length = mTabTittle.length;
        mFragments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    mFragments.add(new SkinFragement());
                    break;
                case 1:
                    mFragments.add(new RecyclerFragement());
                    break;
                case 2:
                    mFragments.add(new DaiLiFragment());
                    break;
                case 3:
                    mFragments.add(new TestTvProviderFragement());
                    break;
                case 4:
                    mFragments.add(new ContentProviderFragement());

                    break;
                default:
                    mFragments.add(new DefaultFragment(mTabTittle[i]));
            }
        }
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getFragmentManager(), mFragments, mTabTittle);
    }
}
