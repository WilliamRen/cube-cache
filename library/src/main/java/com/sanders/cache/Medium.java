package com.sanders.cache;

/**
 * Created by Sanders on 2015/5/13.
 */
public enum  Medium {

    ALL("all"), MEMORY("memory"), DB("database"), DISK("disk");

    private String medium;

    Medium(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return medium;
    }
}
