package com.h.myapplication.login.repo.db;


import com.h.myapplication.login.repo.entity.LoginUserInfo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LoginUserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoginUserInfo info);

    @Query("DELETE FROM login_userinfo_repo")
    void deleteAll();

    //    @Query("SELECT * FROM login_userinfo_repo LIMIT 1")
    @Query("SELECT * FROM login_userinfo_repo WHERE `index` = 0")
    LoginUserInfo getUser();

    @Query("SELECT * FROM login_userinfo_repo")
    LoginUserInfo getAllUser();
}
