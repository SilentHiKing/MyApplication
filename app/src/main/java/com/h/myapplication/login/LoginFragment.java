package com.h.myapplication.login;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
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
    public static final String TAG_1 = "自动登录";

    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_login;
    }

    @Override
    public void initEvent(View view, Bundle savedInstanceState) {
        mLoginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(LoginRepository.getIns()))
                .get(LoginViewModel.class);
        addDisposable( mLoginViewModel.observeViewState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNewState()));

        mLoginViewModel.autoLogin();

        addDisposable( RxView.clicks(btn_signIn)
                .compose(RxUtil.singleClick())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mLoginViewModel.login(login_username.getText().toString(), login_password.getText().toString());
                    }
                }));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Consumer<LoginViewState> onNewState() {
        return new Consumer<LoginViewState>() {
            @Override
            public void accept(LoginViewState state) throws Exception {
                TLog.d(state.toString());
                if (state.throwable != null) {
                    TLog.d(state.throwable.toString());
                    CommonToast.show(state.throwable.toString());
                }
                login_progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
                if (state.autoLoginEvent != null                // has auto login info
                        && state.autoLoginEvent.autoLogin       // allow auto login by user
                ) {
                    login_username.setText(state.autoLoginEvent.username, TextView.BufferType.EDITABLE);
                    login_password.setText(state.autoLoginEvent.password, TextView.BufferType.EDITABLE);

                    mLoginViewModel.onAutoLoginEventUsed();
//                    mLoginViewModel.login(state.autoLoginEvent.username, state.autoLoginEvent.password);
                }
                if (state.loginInfo != null) {
                    TLog.d("登录成功");
                    nav().navigate(R.id.action_loginFragment_to_repoFragment);

//                    ARouter.getInstance().build(ArouterPath.PATH_LOGIN).navigation();
                }

            }
        };
    }

}
