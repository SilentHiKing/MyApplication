package com.hiking.common.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.hiking.common.network.okhttp.OkHttpClientFactory;

import java.io.InputStream;

/**
 * https://muyangmin.github.io/glide-docs-cn/doc/configuration.html
 */
@GlideModule
public class MyOkHttpGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //使用Okhttp3替换默认HttpURLConnection   DataFetcher
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(OkHttpClientFactory.getInstance().getClient());
        registry.replace(GlideUrl.class, InputStream.class, factory);
        super.registerComponents(context, glide, registry);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
