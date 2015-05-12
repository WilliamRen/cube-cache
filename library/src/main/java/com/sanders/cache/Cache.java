package com.sanders.cache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sanders on 2015/5/11.
 */
public class Cache {

    private CacheConfig config;
    private Map<String, Data> cache = new ConcurrentHashMap<String, Data>();


    public Cache(CacheConfig config) {
        this.config = config;
    }

    public Cache() {

    }

    public void putCache(String key, Data data) {
        if (data.getMedium() == CacheMedium.ALL) {
            clearTimeOutData();
            cache.put(key, data);
        } else if (data.getMedium() == CacheMedium.MEMORY) {
            clearTimeOutData();
            cache.put(key, data);
        } else if (data.getMedium() == CacheMedium.DISK) {

        }
    }

    private Data getCache(String key) {
        Data data = cache.get(key);
        if (isTimeOut(data)) {
            cache.remove(key);
            return null;
        }
        return data;
    }

    private boolean isTimeOut(Data data) {
        if (data.getTimeOut() - System.currentTimeMillis() < 0) {
            return true;
        }
        return false;
    }

    private void clearTimeOutData() {
        long time = System.currentTimeMillis();
        for (Map.Entry<String, Data> entry : cache.entrySet()) {
            Data data = entry.getValue();
            if (data.getTimeOut() - time < 0) {
                cache.remove(entry.getKey());
            }
        }
    }

    private void writeDisk(String key, Data data) {
        Object o = data.getData();
        if (o == null) {
            return;
        }

        OutputStream out = null;
        try {
            byte[] buffer = null;
            if (data.getType() == CacheType.BYTES) {
                buffer = (byte[]) o;
                out = new FileOutputStream(config.getDiskPath());
                out.write(buffer);
                out.close();
            } else if (data.getType() == CacheType.STRING) {
                buffer = ((String) o).getBytes();
                out = new FileOutputStream(config.getDiskPath());
            } else if (data.getType() == CacheType.NUMBER) {
                buffer = o.toString().getBytes();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String encryptMD5(String str) {
        try {
            byte[] data = str.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return new BigInteger(1, md.digest()).toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
