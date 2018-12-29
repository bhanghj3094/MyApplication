package com.example.q.myapplication;

import android.graphics.Bitmap;

public class item {
    private String name;
    private String number;
   // private Bitmap photo;
    private int photo;

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

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
