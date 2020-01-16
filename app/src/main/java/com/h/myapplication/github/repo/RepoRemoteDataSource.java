package com.h.myapplication.github.repo;

import com.h.myapplication.base.viewstate.Result;
import com.hiking.common.bean.login.UserRepo;
import com.hiking.common.network.NetworkHelper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RepoRemoteDataSource {
    public Flowable<Result> queryRepo(String username, int pageIndex, int perPage, String sort) {
        return NetworkHelper.getIns().queryRepos(username, pageIndex, perPage, sort)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(new Function<List<UserRepo>, Result>() {
                    @Override
                    public Result apply(List<UserRepo> userRepos) throws Exception {
                        return Result.getSuccess(userRepos);
                    }
                })
                .onErrorReturn(new Function<Throwable, Result>() {
                    @Override
                    public Result apply(Throwable throwable) throws Exception {
                        return Result.getFailure(throwable);
                    }
                });

    }
}

