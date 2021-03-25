package com.example.lingquan.model.domain;

/**
 * Created by RenChao on 2020/5/21.
 */
public class Cachewithduration {
    private long dureatin;
    private String cache;

    public Cachewithduration(long dureatin, String cache) {
        this.dureatin = dureatin;
        this.cache = cache;
    }

    public long getDureatin() {
        return dureatin;
    }

    public void setDureatin(long dureatin) {
        this.dureatin = dureatin;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}


