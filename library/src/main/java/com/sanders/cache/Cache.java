package com.sanders.cache;

import android.content.Context;

import com.sanders.db.DBProxy;

/**
 * Created by Sanders on 2015/5/11.
 */
public class Cache {

    private static Cache mCache;
    private DBProxy db;

    public void initCache(Context context) {
        if (db == null) {
            DBProxy.DBBuilder build = new DBProxy.DBBuilder();
            build.setDbName("cache_db");
            build.setDbVersion(1);
            build.createTable(CacheData.class);
            db = build.build(context);
        }
    }

    public synchronized static Cache getInstance() {
        if (mCache == null) {
            mCache = new Cache();
        }
        return mCache;
    }

    private void putCache(String key, byte[] buffer, CacheType type, long timeOut) {
        CacheData data = new CacheData();
        data.setKey(key);
        data.setCacheType(type);
        data.setData(buffer);
        data.setTimeOut(timeOut);
        long keyId = db.queryKeyId(CacheData.class, "key=?", key);
        if (keyId > 0) {
            data.setPrimaryKeyId(keyId);
            db.update(data);
        } else {
            db.insert(data);
        }
        db.delete(CacheData.class, "time_out>0 AND time_out<=?", String.valueOf(System.currentTimeMillis()));
    }

    public void putCache(String key, String data, long timeOut) {
        this.putCache(key, data.getBytes(), CacheType.STRING, timeOut);
    }

    public void putCache(String key, String data) {
        this.putCache(key, data.getBytes(), CacheType.STRING, -1);
    }

    public void putCache(String key, byte[] data, long timeOut) {
        this.putCache(key, data, CacheType.BYTE, timeOut);
    }

    public void putCache(String key, byte[] data) {
        this.putCache(key, data, CacheType.BYTE, -1);
    }

    private CacheData getCache(String key) {
        CacheData data = db.query(CacheData.class, "key=?", key);
        if (data == null || isTimeOut(data)) {
            return null;
        }
        return data;
    }

    public String getCacheByString(String key) {
        CacheData data = getCache(key);
        if (data != null && data.getData() != null && data.getCacheType() == CacheType.STRING.getType()) {
            return new String(data.getData());
        }
        return null;
    }

    public byte[] getCacheByBytes(String key) {
        CacheData data = getCache(key);
        if (data != null && data.getData() != null && data.getCacheType() == CacheType.BYTE.getType()) {
            return data.getData();
        }
        return null;
    }

    private boolean isTimeOut(CacheData data) {
        if (data.getTimeOut() > 0 && data.getTimeOut() - System.currentTimeMillis() <= 0) {
            db.delete(CacheData.class, data.getPrimaryKeyId());
            return true;
        }
        return false;
    }
}
