package com.example.book;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String title;
    private final int imageid;

    public ShopItem(String title, int imageid) {
        this.title=title;
        this.imageid=imageid;
    }

    public String getTitle() {
        return title;
    }

    public int getImageid() {
        return imageid;
    }

    public void setTitle(String title) { this.title = title; }
}
