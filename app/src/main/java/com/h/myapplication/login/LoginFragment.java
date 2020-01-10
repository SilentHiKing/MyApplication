package com.h.myapplication.login;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.login.interceptor.GitAuthInterceptor;
import com.hiking.common.arouter.ArouterPath;
import com.hiking.common.network.rxjava.RxUtil;
import com.hiking.common.util.CommonToast;
import com.hiking.common.util.TLog;
import com.jakewharton.rxbinding2.view.RxView;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.btn_signIn)
    Button btn_signIn;
    @BindView(R.id.login_progressBar)
    ProgressBar login_progressBar;

    @BindView(R.id.login_username)
    AutoCompleteTextView login_username;
    @BindView(R.id.login_password)
    AutoCompleteTextView login_password;
    LoginViewModel mLoginViewModel;
    public static final String TAG = LoginFragment.class.getSimpleName();

    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_login;
    }

    @Override
    public void initEvent(View view, Bundle savedInstanceState) {
        GitAuthInterceptor.addAuthInterceptor();

        mLoginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(LoginRepository.getIns()))
                .get(LoginViewModel.class);
        RxView.clicks(btn_signIn)
                .compose(RxUtil.singleClick())
                .compose(RxUtil.bindLifecycle(this))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mLoginViewModel.login(login_username.getText().toString(), login_password.getText().toString());
                    }
                })
        ;
        mLoginViewModel.observeViewState()
                .compose(RxUtil.bindLifecycle(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNewState())
        ;
    }


    private Consumer<LoginViewState> onNewState() {
        return new Consumer<LoginViewState>() {
            @Override
            public void accept(LoginViewState state) throws Exception {
                if (state.throwable != null) {
                    TLog.d(state.throwable.toString());
                    CommonToast.show(state.throwable.toString());
                }
                login_progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
                if (state.autoLoginEvent != null                // has auto login info
                        && state.autoLoginEvent.autoLogin       // allow auto login by user
                        && state.useAutoLoginEvent              // ensure auto login info be used one time
                ) {
                    login_username.setText(state.autoLoginEvent.username, TextView.BufferType.EDITABLE);
                    login_password.setText(state.autoLoginEvent.password, TextView.BufferType.EDITABLE);

                    mLoginViewModel.onAutoLoginEventUsed();
                    mLoginViewModel.login(state.autoLoginEvent.username, state.autoLoginEvent.password);
                }
                if (state.loginInfo != null) {
                    TLog.d("登录成功");
                    ARouter.getInstance().build(ArouterPath.PATH_LOGIN).navigation();
                }

            }
        };
    }

}
