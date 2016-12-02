package com.lhd.item;

/**
 * Created by D on 12/2/2016.
 */

public class ItemNotiDTTC {
    private String link;
    private String title;

    @Override
    public String toString() {
        return "ItemNotiDTTC{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ItemNotiDTTC(String link, String title) {

        this.link = link;
        this.title = title;
    }
}
