package com.h.myapplication.login.repo.entity;


import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_userinfo_repo")
public class LoginUserInfo {
    public int index = 0;
    @PrimaryKey
    public Long id;
    @SerializedName("user_access_token")
    @ColumnInfo(name = "user_access_token")
    public String accessToken;

    @SerializedName("auto_login")
    @ColumnInfo(name = "auto_login")
    public boolean isAutoLogin = true;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    public String username;

    @SerializedName("password")
    @ColumnInfo(name = "password")
    public String password;


    public LoginUserInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public LoginUserInfo setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public LoginUserInfo setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
        return this;
    }

    public LoginUserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginUserInfo setPassword(String password) {
        this.password = password;
        return this;
    }
}
