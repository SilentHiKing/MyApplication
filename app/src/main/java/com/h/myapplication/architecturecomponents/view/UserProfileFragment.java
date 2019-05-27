package com.h.myapplication.architecturecomponents.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.myapplication.R;
import com.h.myapplication.architecturecomponents.model.User;
import com.h.myapplication.architecturecomponents.model.UserProfileViewModel;
import com.h.myapplication.base.BaseFragment;

public class UserProfileFragment extends BaseFragment {
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //通过Arguments获取uid
        String userId = getArguments().getString(UID_KEY);
        //创建ViewModel，不必太在意ViewModel的创建形式，这个之后会做详细的分析。现在只需要明白是在哪里生成的就行。
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);


        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                //update UI
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile, container, false);
    }





}
