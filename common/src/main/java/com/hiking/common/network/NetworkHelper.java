package com.hiking.common.network;

import com.hiking.common.network.rxjava.RxUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class NetworkHelper implements INetworkApi {
    private static NetworkHelper instance;
    private static INetworkApi networkApi;

    private NetworkHelper() {
    }

    /**
     * 获取NetworkHelper的实列
     *
     * @return NetworkHelper的实列
     */
    public static NetworkHelper getIns() {
        if (null == instance) {
            synchronized (NetworkHelper.class) {
                if (null == instance) {
                    instance = new NetworkHelper();
                    networkApi = getNetworkApi();
                }
            }
        }
        return instance;
    }

    /**
     * 获取INetworkApi的实现
     *
     * @return INetworkApi的实现
     */
    private static INetworkApi getNetworkApi() {
        //Retrofit方式的实现，若以后更换框架，修改为对应的实现即可
        INetworkApi api = NetworkApiFactory.createNetworkApi(NetworkApiRetrofitImpl.class);
        if (null == api) {
            throw new RuntimeException("没有找到NetworkApi的实现，请检查参数是否正确");
        }
        return api;
    }

    @Override
    public Observable<String> request() {
        return networkApi.request()
                .compose(RxUtil.commonTransformer());
    }

    @Override
    public Observable<ResponseBody> getUsers(int lastIdQueried, int perPage) {
        return networkApi.getUsers(lastIdQueried, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
