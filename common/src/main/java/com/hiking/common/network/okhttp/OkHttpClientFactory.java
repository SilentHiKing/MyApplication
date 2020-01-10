package com.hiking.common.network.okhttp;


import com.hiking.common.MyApplication;
import com.hiking.common.network.urlparser.RetrofitUrlManager;
import com.hiking.common.util.TLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class OkHttpClientFactory {
    private static final String TAG = "OkHttpClientFactory";
    private static OkHttpClientFactory instance = new OkHttpClientFactory();
    private OkHttpClient client = null;

    private OkHttpClientFactory() {

    }

    public static OkHttpClientFactory getInstance() {
        return instance;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            synchronized (OkHttpClientFactory.class) {
                if (client == null) {
                    int mReadTimeout = 15;
                    TLog.d(TAG, "mReadTimeout====" + mReadTimeout);
                    File cacheDir = getCacheDir();
                    Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
                    OkHttpClient.Builder builder = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                            .hostnameVerifier(new TLSSocketFactory.TrustAllHostnameVerifier())   //绕过ssl
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                            .writeTimeout(mReadTimeout, TimeUnit.SECONDS)
                            .addInterceptor(new LogInterceptor().setLevel(LogInterceptor.Level.BODY))
                            .addNetworkInterceptor(new NetCacheInterceptor())
                            .cache(cache);
                    TLSSocketFactory socketFactory = TLSSocketFactory.create();
                    if (socketFactory != null) {
                        builder.sslSocketFactory(socketFactory, socketFactory.trustManager);
                    }
                    client = builder.build();

                }
            }
        }
        return client;
    }

    public void cleanCache() {
        Cache cache = getClient().cache();
        try {
            cache.evictAll();
        } catch (IOException e) {
            TLog.i(TAG, "cleanCache error: " + e);
            e.printStackTrace();
        }
    }

    private File getCacheDir() {
        return new File(MyApplication.sContext.getCacheDir().toString(), "okhttp");
    }


}
