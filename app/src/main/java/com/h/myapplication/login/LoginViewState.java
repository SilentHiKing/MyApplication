package com.h.myapplication.login;

import com.hiking.common.bean.login.UserInfo;

import androidx.annotation.Nullable;

public class LoginViewState {
    final public boolean isLoading;
    final public Throwable throwable;
    final public UserInfo loginInfo;
    final public AutoLoginEvent autoLoginEvent;

    public LoginViewState(Builder builder) {
        isLoading = builder.isLoading;
        throwable = builder.throwable;
        loginInfo = builder.loginInfo;
        autoLoginEvent = builder.autoLoginEvent;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static LoginViewState init() {
        return new LoginViewState.Builder().build();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof LoginViewState) {
            if (obj != this) {
                if (((LoginViewState) obj).isLoading != isLoading
                        || ((LoginViewState) obj).throwable != throwable
                        || ((LoginViewState) obj).loginInfo != loginInfo
                        || ((LoginViewState) obj).autoLoginEvent != autoLoginEvent
                ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public static class Builder {
        public boolean isLoading;
        public Throwable throwable;
        public UserInfo loginInfo;
        public AutoLoginEvent autoLoginEvent;

        public Builder() {
        }

        public Builder setIsLoading(boolean loading) {
            isLoading = loading;
            return this;
        }


        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder setLoginInfo(UserInfo loginInfo) {
            this.loginInfo = loginInfo;
            return this;
        }

        public Builder setAutoLoginEvent(AutoLoginEvent autoLoginEvent) {
            this.autoLoginEvent = autoLoginEvent;
            return this;
        }

        Builder(LoginViewState state) {
            isLoading = state.isLoading;
            throwable = state.throwable;
            loginInfo = state.loginInfo;
            autoLoginEvent = state.autoLoginEvent;
        }

        public LoginViewState build() {
            return new LoginViewState(this);
        }
    }
}
