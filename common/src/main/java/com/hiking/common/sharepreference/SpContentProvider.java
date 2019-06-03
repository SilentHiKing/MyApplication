package com.hiking.common.sharepreference;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;

public class SpContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        SpHelperImp.getIns().init(getContext());
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (Constant.PUT.equals(method)) {
            String key = extras.getString(Constant.KEY);
            Serializable value = extras.getSerializable(Constant.VALUE);
            SpHelperImp.getIns().put(key, value);
        } else if (Constant.GET.equals(method)) {
            String key = extras.getString(Constant.KEY);
            Serializable value = SpHelperImp.getIns().get(key, Serializable.class);
            extras.putSerializable(Constant.VALUE, value);
            return extras;
        } else if (Constant.REMOVE.equals(method)) {
            String key = extras.getString(Constant.KEY);
            SpHelperImp.getIns().remove(key);
        }
        else if (Constant.CLEAR.equals(method)) {
            SpHelperImp.getIns().clear();
        }
        return super.call(method, arg, extras);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
