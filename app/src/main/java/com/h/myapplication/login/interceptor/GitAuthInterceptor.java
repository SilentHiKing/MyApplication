package com.h.myapplication.login.interceptor;

import android.text.TextUtils;
import android.util.Base64;

import com.h.myapplication.login.LoginRepository;
import com.h.myapplication.login.repo.entity.LoginUserInfo;
import com.hiking.common.network.Api;
import com.hiking.common.network.okhttp.OkHttpClientFactory;
import com.hiking.common.util.TLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitAuthInterceptor implements Interceptor {


    public static void addAuthInterceptor() {
        TLog.d("添加git请求权限");
        OkHttpClient client = OkHttpClientFactory.getInstance().getClient()
                .newBuilder()
                .addInterceptor(new GitAuthInterceptor())
                .build();
        OkHttpClientFactory.getInstance().setClient(client);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        if (url != null) {
            TLog.d(url.toString());
            if (url.toString().contains(Api.APP_GITHUB_DOMAIN)) {
                String auth = getAuthorization();
                if (!TextUtils.isEmpty(auth)) {
                    TLog.d(auth);
                    request = request.newBuilder()
                            .addHeader("Authorization", auth)
                            .build();
                }
            }
        }
        return chain.proceed(request);
    }

    private String getAuthorization() {
        LoginUserInfo info = LoginRepository.getIns().mLoginLocalDataSource.getUser();
        TLog.d(info.toString());
        if (info != null) {
            String accessToken = info.accessToken;
            String username = info.username;
            String password = info.password;
            if (TextUtils.isEmpty(accessToken)) {
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    String s = Base64.encodeToString(
                            String.format("%s:%s", username, password).getBytes()
                            , Base64.NO_WRAP);
                    return String.format("basic %s", s);
                }
            } else {
                return String.format("token %s", accessToken);
            }
        }
        return null;
    }


}
