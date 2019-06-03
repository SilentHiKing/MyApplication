package com.h.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.test.Book;
import com.hiking.common.sharepreference.SpHelper;

import java.io.Serializable;

import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class DaiLiFragment extends BaseFragment {

    public DaiLiFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daili,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        SpHelper.getIns().init(getContext());
        SpHelper.getIns().put("hello", new Book("你好",1));
        Log.d("hello11","gggg");
        Book i =SpHelper.getIns().get("hello",Book.class);
        Log.d("hello",SpHelper.getIns().get("hello",Integer.class)+"ggg");
    }
}
