package com.sanders.cache;

/**
 * Created by Sanders on 2015/5/12.
 */
public class CacheConfig {

    private String diskPath;

    public CacheConfig(){}

    public CacheConfig(String diskPath){
        this.diskPath = diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public String getDiskPath() {
        return diskPath;
    }
}
