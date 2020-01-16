package com.h.myapplication.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.myapplication.R;
import com.hiking.common.util.TLog;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseFragment extends Fragment {
    CompositeDisposable disposables;
    View mRootView;

    Unbinder mUnbinder;

    protected void addDisposable(Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TLog.d("onCreateView");
        if (mRootView == null) {
            mRootView = inflater.inflate(generateLayoutResID(), container, false);
            mUnbinder = ButterKnife.bind(this, mRootView);
            initView(mRootView, savedInstanceState);
            initEvent(mRootView, savedInstanceState);
        }
        return mRootView;
    }

    public void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TLog.d("onViewCreated");

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
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (disposables != null) {
            disposables.clear();
            disposables = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
