package com.h.myapplication.login;

import android.text.TextUtils;

import com.h.myapplication.base.viewstate.Result;
import com.h.myapplication.bean.Errors;
import com.hiking.common.bean.login.UserInfo;
import com.hiking.common.network.rxjava.BehaviorSubjectUtil;
import com.hiking.common.network.rxjava.bean.Either;
import com.hiking.common.util.TLog;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class LoginViewModel extends ViewModel {
    LoginRepository mLoginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        mLoginRepository = loginRepository;
    }

    private final BehaviorSubject<LoginViewState> mViewStateSubject = BehaviorSubject.create();

    public Observable<LoginViewState> observeViewState() {
        mViewStateSubject.onNext(LoginViewState.init());
        return mViewStateSubject.hide().distinctUntilChanged();
    }

    public void autoLogin() {
        TLog.d(LoginFragment.TAG_1, "开始");
        mLoginRepository.fetchAutoLogin()
//                .singleOrError()
                .onErrorReturn(new Function<Throwable, AutoLoginEvent>() {
                    @Override
                    public AutoLoginEvent apply(Throwable throwable) throws Exception {
                        TLog.d(LoginFragment.TAG_1, throwable.toString());
                        return new AutoLoginEvent();
                    }
                })
                .subscribe(new Consumer<AutoLoginEvent>() {
                    @Override
                    public void accept(AutoLoginEvent autoLoginEvent) throws Exception {
                        TLog.d(LoginFragment.TAG_1, "获取到用户信息成功,start");
                        BehaviorSubjectUtil.assertNoNullValue(mViewStateSubject);
                        TLog.d(LoginFragment.TAG_1, "获取到用户信息成功,end");
                        mViewStateSubject.onNext(mViewStateSubject.getValue().newBuilder()
                                .setIsLoading(false)
                                .setAutoLoginEvent(autoLoginEvent)
                                .setLoginInfo(null)
                                .setThrowable(null)
                                .build());
                    }
                });

    }


    public void onAutoLoginEventUsed() {
        BehaviorSubjectUtil.assertNoNullValue(mViewStateSubject);
        mViewStateSubject.onNext(mViewStateSubject.getValue().newBuilder()
                .setIsLoading(false)
                .setAutoLoginEvent(null)
                .setLoginInfo(null)
                .setThrowable(null)
                .build());

    }


    public void login(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            BehaviorSubjectUtil.assertNoNullValue(mViewStateSubject);
            mViewStateSubject.onNext(mViewStateSubject.getValue().newBuilder()
                    .setIsLoading(false)
                    .setAutoLoginEvent(null)
                    .setLoginInfo(null)
                    .setThrowable(Errors.ERROR_EMPTY_INPUT)
                    .build());
        } else {
            mLoginRepository.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<Either<UserInfo, Throwable>, Result>() {

                        @Override
                        public Result apply(Either<UserInfo, Throwable> either) throws Exception {
                            if (either.isLeft()) {
                                return Result.getSuccess(either.getLeft());
                            }
                            return Result.getFailure(either.getRight());
                        }
                    })
                    .startWith(Result.mLoading)
                    .onErrorReturn(new Function<Throwable, Result>() {
                        @Override
                        public Result apply(Throwable throwable) throws Exception {
                            return Result.getFailure(throwable);
                        }
                    })
                    .subscribe(new Consumer<Result>() {
                        @Override
                        public void accept(Result result) throws Exception {
                            BehaviorSubjectUtil.assertNoNullValue(mViewStateSubject);
                            boolean isLoading = false;
                            Throwable throwable = null;
                            UserInfo loginInfo = null;
                            if (result instanceof Result.Loading) {
                                isLoading = true;
                                throwable = null;
                                loginInfo = null;
                            } else if (result instanceof Result.Idle) {
                                isLoading = false;
                                throwable = null;
                                loginInfo = null;
                            } else if (result instanceof Result.Failure) {
                                isLoading = false;
                                throwable = (Throwable) result.get();
                                loginInfo = null;

                            } else if (result instanceof Result.Success) {
                                isLoading = false;
                                throwable = null;
                                loginInfo = (UserInfo) result.get();
                            }
                            mViewStateSubject.onNext(mViewStateSubject.getValue().newBuilder()
                                    .setIsLoading(isLoading)
                                    .setLoginInfo(loginInfo)
                                    .setThrowable(throwable)
                                    .build());
                        }
                    })

            ;
        }
    }
}
