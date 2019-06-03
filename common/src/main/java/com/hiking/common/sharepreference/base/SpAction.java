package com.hiking.common.sharepreference.base;

import java.io.Serializable;

public interface SpAction {

    /**
     * 存入数据
     *
     * @param key
     * @param value
     */
    void put(String key, Serializable value);

    /**
     * 获取数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除数据
     *
     * @param key
     */
    void remove(String key);

    /**
     * 删除全部数据
     */
    void clear();

}
