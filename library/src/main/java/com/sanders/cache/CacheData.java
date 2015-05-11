package com.sanders.cache;

/**
 * Created by Sanders on 2015/5/11.
 */
public class CacheData {

    /**
     * 缓存介质
     */
    private CacheMedium medium;

    /**
     * 缓存类型
     */
    private CacheType type;

    /**
     * 超时时间
     */
    private long timeOut;

    /**
     * 数据
     */
    private Object data;

}
