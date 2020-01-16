package com.h.myapplication.github.repo;

import com.h.myapplication.base.viewstate.Result;
import com.h.myapplication.login.UserManager;
import com.hiking.common.bean.login.UserRepo;

import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.paging.DataSource;
import io.reactivex.Flowable;

public class RepoRepository {
    public RepoRemoteDataSource mRepoRemoteDataSource;
    public RepoLocalDataSource mRepoLocalDataSource;

    public static RepoRepository getIns() {
        return RepoRepositoryHolder.INSTANCE;
    }

    private static class RepoRepositoryHolder {
        private final static RepoRepository INSTANCE = new RepoRepository();
    }

    private RepoRepository() {
        mRepoRemoteDataSource = new RepoRemoteDataSource();
        mRepoLocalDataSource = new RepoLocalDataSource();
    }
    public static final int PAGING_REMOTE_PAGE_SIZE =30;
    String SORT = "updated";

    @MainThread
    public Flowable<Result> fetchRepoByPage(int pageIndex) {
        return mRepoRemoteDataSource
                .queryRepo(UserManager.mUserInfo.login, pageIndex, PAGING_REMOTE_PAGE_SIZE, SORT);
    }

    @MainThread
    public DataSource.Factory<Integer, UserRepo> fetchRepoDataSourceFactory() {
        return mRepoLocalDataSource.fetchRepoDataSourceFactory();
    }

    @WorkerThread
    public void clearAndInsertNewData(List<UserRepo> items) {
        mRepoLocalDataSource.clearOldAndInsertNewData(items);
    }

    @WorkerThread
    public void insertNewPageData(List<UserRepo> items) {
        mRepoLocalDataSource.insertNewPageData(items);
    }
}
