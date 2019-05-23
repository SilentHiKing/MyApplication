package com.example.h.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.h.myapplication.adapter.TabFragmentPagerAdapter;
import com.example.h.myapplication.base.BaseFragment;
import com.example.h.myapplication.fragment.ContentProviderFragement;
import com.example.h.myapplication.fragment.DaiLiFragment;
import com.example.h.myapplication.fragment.DefaultFragment;
import com.example.h.myapplication.fragment.RecyclerFragement;
import com.example.h.myapplication.fragment.TestTvProviderFragement;
import com.example.h.myapplication.test.DatabaseInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(android.R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    String[] mTabTittle;
    List<BaseFragment> mFragments;
    TabFragmentPagerAdapter mTabFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        test();
        initData();
        initView();

    }

    private void test() {
        DatabaseInterface.getInstance(this).insert();
        DatabaseInterface.getInstance(this).query();
    }

    private void initView() {
        setSupportActionBar(mToolBar);
        mViewPager.setAdapter(mTabFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void initData() {
        mTabTittle = getResources().getStringArray(R.array.tab_tittle);
        int length = mTabTittle.length;
        mFragments = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    mFragments.add(new ContentProviderFragement());

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
                default:
                    mFragments.add(new DefaultFragment(mTabTittle[i]));
            }
        }
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabTittle);
    }
}
