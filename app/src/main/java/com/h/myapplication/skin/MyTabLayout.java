package com.h.myapplication.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.h.myapplication.R;
import com.hiking.common.skin.SkinViewSupport;
import com.hiking.common.skin.util.SkinResource;


public class MyTabLayout extends TabLayout implements SkinViewSupport {
    int tabIndicatorColorResId;
    int tabTextColorResId;

    public MyTabLayout(Context context) {
        this(context, null, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,
                defStyleAttr, 0);
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
        a.recycle();
    }

    @Override
    public void applySkin() {

        int tabIndicatorColor = SkinResource.getInstance().getColor(tabIndicatorColorResId);
        setSelectedTabIndicatorColor(tabIndicatorColor);

        ColorStateList colorStateList = SkinResource.getInstance().getColorStateList(tabTextColorResId);
        setTabTextColors(colorStateList);
    }

}
