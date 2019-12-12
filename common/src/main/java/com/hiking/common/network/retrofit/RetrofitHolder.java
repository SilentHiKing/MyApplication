package com.hiking.common.network.retrofit;

import com.google.gson.GsonBuilder;
import com.hiking.common.network.okhttp.OkHttpClientFactory;
import com.hiking.common.network.urlparser.RetrofitUrlManager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hiking.common.network.Api.APP_DEFAULT_DOMAIN;
import static com.hiking.common.network.Api.APP_DOUBAN_DOMAIN;
import static com.hiking.common.network.Api.APP_GANK_DOMAIN;
import static com.hiking.common.network.Api.APP_GITHUB_DOMAIN;
import static com.hiking.common.network.Api.DOUBAN_DOMAIN_NAME;
import static com.hiking.common.network.Api.GANK_DOMAIN_NAME;
import static com.hiking.common.network.Api.GITHUB_DOMAIN_NAME;

public class RetrofitHolder {

    private static Retrofit mBaseRetrofit;

    /**
     * 获取接口api
     *
     * @param clazz 接口api的class
     * @param <T>   接口类型
     * @return 返回接口Api
     */
    public static <T> T getInterfaceApi(Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        if (mBaseRetrofit == null) {
            RetrofitUrlManager.getInstance().putDomain(GITHUB_DOMAIN_NAME, APP_GITHUB_DOMAIN);
            RetrofitUrlManager.getInstance().putDomain(GANK_DOMAIN_NAME, APP_GANK_DOMAIN);
            RetrofitUrlManager.getInstance().putDomain(DOUBAN_DOMAIN_NAME, APP_DOUBAN_DOMAIN);
            mBaseRetrofit = new Retrofit.Builder()
                    .baseUrl(APP_DEFAULT_DOMAIN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()  // 设置GSON的非严格模式setLenient()
                            .create()))//使用Gson
                    .client(OkHttpClientFactory.getInstance().getClient())
                    .build();
        }
        return mBaseRetrofit.create(clazz);
    }

}
