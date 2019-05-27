package com.h.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class DefaultFragment extends BaseFragment {

    private final String mTag;
    @BindView(R.id.tag)
    AutoCompleteTextView mTextView;

    @SuppressLint("ValidFragment")
    public DefaultFragment(String tag) {
        mTag = tag;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        mTextView.setText(mTag);
    }

}
