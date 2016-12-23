package com.lhd.object;

/**
 * Created by D on 12/19/2016.
 */

public class Version {
    public String url;
    public String verstionName;
    public String content;
    @Override
    public String toString() {
        return "Version{" +
                "url='" + url + '\'' +
                ", verstionName='" + verstionName + '\'' +
                '}';
    }
    public Version() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVerstionName() {
        return verstionName;
    }

    public void setVerstionName(String verstionName) {
        this.verstionName = verstionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Version(String url, String verstionName, String content) {
        this.url = url;
        this.verstionName = verstionName;
        this.content = content;
    }
}
