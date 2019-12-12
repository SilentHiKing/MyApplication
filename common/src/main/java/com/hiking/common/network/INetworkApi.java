package com.hiking.common.network;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.hiking.common.network.Api.GITHUB_DOMAIN_NAME;
import static com.hiking.common.network.urlparser.RetrofitUrlManager.DOMAIN_NAME_HEADER;
import static com.hiking.common.network.urlparser.RetrofitUrlManager.IDENTIFICATION_IGNORE;

interface INetworkApi {

    @GET(IDENTIFICATION_IGNORE)
    Observable<String> request();

    /**
     * 如果不需要多个 BaseUrl, 继续使用初始化时传入 Retrofit 中的默认 BaseUrl, 就不要加上 DOMAIN_NAME_HEADER 这个 Header
     */
    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    @GET("/users")
    Observable<ResponseBody> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
