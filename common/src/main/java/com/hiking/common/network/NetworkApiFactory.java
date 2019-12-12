package com.hiking.common.network;

/**
 * 创建NetworkApi的工厂类
 */
class NetworkApiFactory {
    /**
     * 根据传入不同的class，创建对应的INetworkApi实现
     * @param clazz {@link INetworkApi}实现类的class
     * @return 返回INetworkApi的实现
     */
    public static INetworkApi createNetworkApi(Class<? extends INetworkApi> clazz){
        INetworkApi result = null;
        if(null != clazz) {
            Class[] interfaces = clazz.getInterfaces();
            for (Class tmpInterface :
                    interfaces) {
                if(tmpInterface == INetworkApi.class){
                    try {
                        result = clazz.newInstance();
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
