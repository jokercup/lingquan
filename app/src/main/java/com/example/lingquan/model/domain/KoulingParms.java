package com.example.lingquan.model.domain;

/**
 * Created by RenChao on 2020/5/15.
 */
public class KoulingParms {

    public String url;

    public String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "KoulingParms{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public KoulingParms(String url, String title) {
        this.url = url;
        this.title = title;
    }
}
