package com.t3h.miniproject.model;

import android.view.MenuItem;

import androidx.annotation.DrawableRes;

public class NationalFlag {
    private String keylanguage;
    private int img;

    public NationalFlag(String keylanguage, int img) {
        this.keylanguage = keylanguage;
        this.img = img;
    }

    public String getKeylanguage() {
        return keylanguage;
    }

    public void setKeylanguage(String keylanguage) {
        this.keylanguage = keylanguage;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
