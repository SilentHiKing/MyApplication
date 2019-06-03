package com.hiking.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.hiking.common.sharepreference.base.SpAction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;


public class SpHelperImp implements SpAction {

    private static class SpHelperImpHolder {
        private static final SpHelperImp mSpHelperImp = new SpHelperImp();
    }

    public static SpHelperImp getIns() {
        return SpHelperImpHolder.mSpHelperImp;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private static final String SPNAME = "hello";

    private static Context mContext;

    private SharedPreferences getSp() {
        if (mContext == null) {
            throw new IllegalStateException("请初始化SpHelperImp:mContext");
        }
        return mContext.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
    }

    private SoftReference<Map<String, Object>> mCacheMap;

    private Object getCachedValue(String name) {
        if (mCacheMap != null) {
            Map<String, Object> map = mCacheMap.get();
            if (map != null) {
                return map.get(name);
            }
        }
        return null;
    }

    private void removeCachedValue(String name) {
        if (mCacheMap != null) {
            Map<String, Object> map = mCacheMap.get();
            if (map != null) {
                map.remove(name);
            }
        }
    }

    private void cleanCachedValue() {
        Map<String, Object> map;
        if (mCacheMap != null) {
            map = mCacheMap.get();
            if (map != null) {
                map.clear();
            }
        }
    }

    private void setValueToCached(String name, Object value) {
        Map<String, Object> map;
        if (mCacheMap == null) {
            map = new HashMap<>();
            mCacheMap = new SoftReference<Map<String, Object>>(map);
        } else {
            map = mCacheMap.get();
            if (map == null) {
                map = new HashMap<>();
                mCacheMap = new SoftReference<Map<String, Object>>(map);
            }
        }
        map.put(name, value);
    }

    @Override
    public void put(String key, Serializable object) {
        if (object.equals(getCachedValue(key))) {
            return;
        }
        SharedPreferences.Editor editor = getSp().edit();
        if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else {
            //针对复杂类型存储<对象>
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(baos);
                out.writeObject(object);
                String objectVal = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
                editor.putString(key, objectVal);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        editor.commit();
        setValueToCached(key, object);
    }


    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object value = getCachedValue(key);
        if (value != null) {
            return (T) value;
        }
        SharedPreferences sp = getSp();
        if (!sp.contains(key)) {
            return null;
        }
        if (Boolean.class.equals(clazz)) {
            value = sp.getBoolean(key, false);
        } else if (String.class.equals(clazz)) {
            value = sp.getString(key, null);
        } else if (Integer.class.equals(clazz)) {
            value = sp.getInt(key, -1);
        } else if (Long.class.equals(clazz)) {
            value = sp.getLong(key, -1L);
        } else if (Float.class.equals(clazz)) {
            value = sp.getFloat(key, -1F);
        } else {
            String objectVal = sp.getString(key, null);
            byte[] buffer = android.util.Base64.decode(objectVal, android.util.Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                value = ois.readObject();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) value;
    }

    @Override
    public void remove(String key) {
        SharedPreferences.Editor editor = getSp().edit();
        editor.remove(key);
        editor.commit();
        removeCachedValue(key);
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = getSp().edit();
        editor.clear();
        editor.commit();
        cleanCachedValue();
    }
}
