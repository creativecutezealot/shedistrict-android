package com.appentus.shedistrict.models;

import android.graphics.drawable.Drawable;

public class IntroBean {
    Drawable image;
    String title ;
    String dis ;

    public IntroBean(Drawable image, String title, String dis) {
        this.image = image;
        this.title = title;
        this.dis = dis;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }
}
