package com.example.q.myapplication;

import android.graphics.Bitmap;

public class item {
    private String name;
    private String number;
    private Bitmap photo;

    public item(String name,String number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
