package com.sanders.cache;

/**
 * Created by Sanders on 2015/5/11.
 */
public enum CacheType {

    UNKNOWN(-1), BYTE(0), STRING(1);

    private int type;

    CacheType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
