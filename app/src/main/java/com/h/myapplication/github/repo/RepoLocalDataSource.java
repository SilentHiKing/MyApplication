package com.h.myapplication.github.repo;

import com.h.myapplication.login.repo.db.UserDatabase;
import com.h.myapplication.login.repo.db.UserReposDao;
import com.hiking.common.bean.login.UserRepo;

import java.util.List;

import androidx.annotation.WorkerThread;
import androidx.paging.DataSource;

class RepoLocalDataSource {
    UserReposDao userReposDao;

    public RepoLocalDataSource() {
        userReposDao = UserDatabase.getIns().userReposDao();
    }


    public DataSource.Factory<Integer, UserRepo> fetchRepoDataSourceFactory() {
        return userReposDao.queryRepos();
    }

    @WorkerThread
    public void clearOldAndInsertNewData(List<UserRepo> newPage) {
        UserDatabase.getIns().runInTransaction(new Runnable() {
            @Override
            public void run() {
                userReposDao.deleteAllRepos();
                insertDataInternal(newPage);
            }
        });
    }

    @WorkerThread
    public void insertNewPageData(List<UserRepo> newPage) {
        UserDatabase.getIns().runInTransaction(new Runnable() {
            @Override
            public void run() {
                insertDataInternal(newPage);
            }
        });
    }

    public void insertDataInternal(List<UserRepo> newPage) {
        if (newPage == null) {
            return;
        }
        int start = userReposDao.getNextIndexInRepos();
        for (int i = 0; i < newPage.size(); i++) {
            newPage.get(i).indexInSortResponse = start + i;
        }
        userReposDao.insert(newPage);
    }
}
