package com.h.myapplication.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.myapplication.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(generateLayoutResID(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent(view, savedInstanceState);
    }

    public void initEvent(View view, Bundle savedInstanceState) {
    }

    public int generateLayoutResID() {
        return R.layout.fragment_default;
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
