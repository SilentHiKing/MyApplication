package com.hiking.common.network;


import com.hiking.common.bean.login.LoginRequest;
import com.hiking.common.bean.login.ReceivedEvent;
import com.hiking.common.bean.login.UserAccessToken;
import com.hiking.common.bean.login.UserInfo;
import com.hiking.common.bean.login.UserRepo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.hiking.common.network.Api.GITHUB_DOMAIN_NAME;
import static com.hiking.common.network.urlparser.RetrofitUrlManager.DOMAIN_NAME_HEADER;
import static com.hiking.common.network.urlparser.RetrofitUrlManager.IDENTIFICATION_IGNORE;

interface INetworkApi {

    @GET(IDENTIFICATION_IGNORE)
    Observable<String> request();

    /**
     * 获取github的登录token
     * @param authRequest
     * @return
     */
    @POST("authorizations")
    @Headers({"Accept: application/json",DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    Flowable<UserAccessToken> authorizations(@Body LoginRequest authRequest);


    @GET("user")
    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    Flowable<UserInfo> fetchUserOwner();

    @GET("users/{username}/received_events?")
    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    Flowable<List<ReceivedEvent>> queryReceivedEvents(@Path("username")String username,
                                                      @Query("page")int pageIndex,
                                                      @Query("per_page")int perPage);

    @GET("users/{username}/repos?")
    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    Flowable<List<UserRepo>> queryRepos(@Path("username") String username,
                                        @Query("page") int pageIndex,
                                        @Query("per_page") int perPage,
                                        @Query("sort")String sort);


    /**
     * 如果不需要多个 BaseUrl, 继续使用初始化时传入 Retrofit 中的默认 BaseUrl, 就不要加上 DOMAIN_NAME_HEADER 这个 Header
     */
    @Headers({DOMAIN_NAME_HEADER + GITHUB_DOMAIN_NAME})
    @GET("/users")
    Observable<ResponseBody> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
