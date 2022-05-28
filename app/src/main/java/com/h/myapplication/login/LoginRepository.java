package com.h.myapplication.login;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.h.myapplication.base.repo.ILocalDataSource;
import com.h.myapplication.base.repo.IRemoteDataSource;
import com.h.myapplication.login.repo.db.LoginUserInfoDao;
import com.h.myapplication.login.repo.db.UserDatabase;
import com.h.myapplication.login.repo.entity.LoginUserInfo;
import com.hiking.common.BuildConfig;
import com.hiking.common.bean.login.LoginRequest;
import com.hiking.common.bean.login.UserAccessToken;
import com.hiking.common.bean.login.UserInfo;
import com.hiking.common.network.NetworkHelper;
import com.hiking.common.network.rxjava.bean.Either;
import com.hiking.common.util.TLog;
import com.hiking.common.util.Util;

import java.util.Arrays;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.h.myapplication.login.UserManager.mUserInfo;

public class LoginRepository {
    public LoginRemoteDataSource mLoginRemoteDataSource;
    public LoginLocalDataSource mLoginLocalDataSource;

    public static LoginRepository getIns() {
        return LoginRepositoryHolder.INSTANCE;
    }

    private static class LoginRepositoryHolder {
        private final static LoginRepository INSTANCE = new LoginRepository();
    }

    private LoginRepository() {
        mLoginRemoteDataSource = new LoginRemoteDataSource();
        mLoginLocalDataSource = new LoginLocalDataSource();
    }

    public Flowable<AutoLoginEvent> fetchAutoLogin() {
        return mLoginLocalDataSource.fetchAutoLogin();
    }

    public Flowable<Either<UserInfo, Throwable>> login(String username, String password) {
        return mLoginLocalDataSource.saveUser(username, password)
                .andThen(mLoginRemoteDataSource.login())
                .doOnNext(new Consumer<Either<UserInfo, Throwable>>() {
                    @Override
                    public void accept(Either<UserInfo, Throwable> either) throws Exception {

                        if (either.isRight()) {
                            //如果登录失败，清除登录信息
                            mLoginLocalDataSource.clearUser();
                        } else {
                            //保存用户信息
                            mUserInfo = either.getLeft();
                            TLog.d(LoginFragment.TAG, "已经登录成功");
                        }
                    }
                })
                //中间执行的流程出错,此处不应该会走到
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mLoginLocalDataSource.clearUser();
                    }
                });
    }


    static class LoginRemoteDataSource implements IRemoteDataSource {
        public Flowable<Either<UserInfo, Throwable>> login() {
            return NetworkHelper.getIns().authorizations(new LoginRequest.Builder()
                    .setScopes(Arrays.asList("user", "repo", "gist", "notifications"))
                    .setNote(Util.getApplicationByReflect().getPackageName())
                    .setClientId(BuildConfig.GITHUB_CLIENT_ID)
                    .setClientSecret(BuildConfig.GITHUB_CLIENT_SECRET)
                    .build())
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<UserAccessToken, Flowable<UserInfo>>() {
                        @Override
                        public Flowable<UserInfo> apply(UserAccessToken userAccessToken) throws Exception {
                            return NetworkHelper.getIns().fetchUserOwner();
                        }
                    })
                    .map(new Function<UserInfo, Either<UserInfo, Throwable>>() {
                        @Override
                        public Either<UserInfo, Throwable> apply(UserInfo userInfo) throws Exception {
                            if (userInfo == null) {
                                return new Either<UserInfo, Throwable>().right(new RuntimeException("网络返回的userinfo是null"));
                            }
                            return new Either<UserInfo, Throwable>().left(userInfo);
                        }
                    })
                    .onErrorReturn(new Function<Throwable, Either<UserInfo, Throwable>>() {
                        @Override
                        public Either<UserInfo, Throwable> apply(Throwable throwable) throws Exception {
                            TLog.d(LoginFragment.TAG, throwable.toString());
                            return new Either<UserInfo, Throwable>().right(throwable);
                        }
                    });
        }
    }


    static public class LoginLocalDataSource implements ILocalDataSource {
        LoginUserInfoDao mLoginUserInfoDao;

        public LoginLocalDataSource() {
            mLoginUserInfoDao = UserDatabase.getIns(Util.getApplicationByReflect()).loginUserInfoDao();
        }

        public LoginUserInfo getUser() {
            return mLoginUserInfoDao.getUser();
        }

        Completable saveUser(String username, String password) {
            return Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    mLoginUserInfoDao.insert(
                            new LoginUserInfo()
                                    .setUsername(username)
                                    .setPassword(password)
                    );
                }
            });
        }

        public Completable clearUser() {
            return Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    mLoginUserInfoDao.deleteAll();
                }
            })/*.subscribeOn(Schedulers.io())*/;
        }

        @SuppressLint("CheckResult")
        public Flowable<AutoLoginEvent> fetchAutoLogin() {
            return Flowable.create(new FlowableOnSubscribe<AutoLoginEvent>() {
                @Override
                public void subscribe(FlowableEmitter<AutoLoginEvent> emitter) throws Exception {
                    TLog.d(LoginFragment.TAG_1,"获取本地用户信息");
                    LoginUserInfo info = mLoginUserInfoDao.getUser();
                    boolean isAuto = (!TextUtils.isEmpty(info.username)
                            && !TextUtils.isEmpty(info.password)
                            && info.isAutoLogin);
                    emitter.onNext(isAuto ? new AutoLoginEvent().setAutoLogin(isAuto)
                            .setUsername(info.username).setPassword(info.password)
                            : new AutoLoginEvent().setAutoLogin(isAuto));
                    emitter.onComplete();
                }
            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
        }
    }
}
