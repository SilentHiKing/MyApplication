package com.hiking.common.network;

import com.hiking.common.bean.login.LoginRequest;
import com.hiking.common.bean.login.ReceivedEvent;
import com.hiking.common.bean.login.UserAccessToken;
import com.hiking.common.bean.login.UserInfo;
import com.hiking.common.bean.login.UserRepo;
import com.hiking.common.network.retrofit.RetrofitHolder;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

class NetworkApiRetrofitImpl implements INetworkApi {
    INetworkApi networkApi = RetrofitHolder.getInterfaceApi(INetworkApi.class);

    @Override
    public Observable<String> request() {
        return networkApi.request();
    }

    @Override
    public Flowable<UserAccessToken> authorizations(LoginRequest authRequest) {
        return networkApi.authorizations(authRequest);
    }

    @Override
    public Flowable<UserInfo> fetchUserOwner() {
        return networkApi.fetchUserOwner();
    }

    @Override
    public Flowable<List<ReceivedEvent>> queryReceivedEvents(String username, int pageIndex, int perPage) {
        return networkApi.queryReceivedEvents(username,pageIndex,perPage);
    }

    @Override
    public Flowable<List<UserRepo>> queryRepos(String username, int pageIndex, int perPage, String sort) {
        return networkApi.queryRepos(username,pageIndex,perPage,sort);
    }

    @Override
    public Observable<ResponseBody> getUsers(int lastIdQueried, int perPage) {
        return networkApi.getUsers(lastIdQueried, perPage);
    }

}
