package com.h.myapplication.login;

public class AutoLoginEvent {
    boolean autoLogin;
    String username;
    String password;

    public AutoLoginEvent setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
        return this;
    }

    public AutoLoginEvent setUsername(String username) {
        this.username = username;
        return this;
    }

    public AutoLoginEvent setPassword(String password) {
        this.password = password;
        return this;
    }
}
