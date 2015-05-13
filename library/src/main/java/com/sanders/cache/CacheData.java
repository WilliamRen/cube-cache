package com.sanders.cache;

import com.sanders.db.IDColumn;

import java.util.Date;

/**
 * Created by Sanders on 2015/5/11.
 */
public class CacheData extends IDColumn {

    private String key;
    private int cacheType = CacheType.UNKNOWN.getType();
    private byte[] data;
    private Date cacheDate = new Date(System.currentTimeMillis());
    private long timeOut = -1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType.getType();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getCacheDate() {
        return cacheDate;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        if (timeOut > 0) {
            this.timeOut = cacheDate.getTime() + timeOut;
        }
    }
}
