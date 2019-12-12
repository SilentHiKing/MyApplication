package com.hiking.common.network;

import com.hiking.common.network.retrofit.RetrofitHolder;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

class NetworkApiRetrofitImpl implements INetworkApi {
    INetworkApi networkApi = RetrofitHolder.getInterfaceApi(INetworkApi.class);

    @Override
    public Observable<String> request() {
        return networkApi.request();
    }

    @Override
    public Observable<ResponseBody> getUsers(int lastIdQueried, int perPage) {
        return networkApi.getUsers(lastIdQueried, perPage);
    }

}
