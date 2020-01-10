package com.h.myapplication.login.repo.db;


import com.hiking.common.bean.login.UserRepo;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
interface UserReposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserRepo> repos);

    @Query("SELECT * FROM user_repos ORDER BY indexInSortResponse ASC")
    DataSource.Factory<Integer, UserRepo> queryRepos();

    @Query("DELETE FROM user_repos")
    void deleteAllRepos();

    @Query("SELECT MAX(indexInSortResponse) + 1 FROM user_repos")
    int getNextIndexInRepos();
}
