package com.hiking.common.sharepreference;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.hiking.common.sharepreference.base.SpAction;

import java.io.Serializable;

/**
 * 可跨进程读写Sp帮助类
 * 可以存实现Serializable的对象
 */
public class SpHelper implements SpAction {

    private static class SpHelperHolder {
        private static final SpHelper mSpHelper = new SpHelper();
    }

    Context mContext;

    public static SpHelper getIns() {
        return SpHelperHolder.mSpHelper;
    }

    /**
     * 初始化工具
     *
     * @param c
     */
    public void init(Context c) {
        mContext = c.getApplicationContext();
        SpHelperImp.getIns().init(mContext);
    }

    public Context getCon() {
        if (mContext == null) {
            throw new IllegalStateException("请初始化SpHelper:mContext");
        }
        return mContext;
    }


    @Override
    public void put(String key, Serializable value) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY, key);
        bundle.putSerializable(Constant.VALUE, value);
        getCon().getContentResolver().call(getUri(), Constant.PUT, null, bundle);
    }


    @Override
    public <T> T get(String key, Class<T> clazz) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY, key);
        bundle = getCon().getContentResolver().call(getUri(), Constant.GET, null, bundle);
        Serializable value = bundle.getSerializable(Constant.VALUE);
        Log.d(Constant.TAG, value + value.getClass().getSimpleName() + "_" + value);
        return (T) value;

    }

    @Override
    public void remove(String key) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.KEY, key);
        getCon().getContentResolver().call(getUri(), Constant.REMOVE, null, bundle);
    }

    @Override
    public void clear() {
        getCon().getContentResolver().call(getUri(), Constant.CLEAR, null, null);
    }

    public Uri getUri() {
        Uri uri = Uri.parse(Constant.CONTENT_URI + Constant.SEPARATOR + "*");
        return uri;
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型
     */
    private boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

}
