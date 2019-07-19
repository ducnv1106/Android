package com.t3h.miniproject.model;

import android.view.MenuItem;

import androidx.annotation.DrawableRes;

public class NationalFlag {
    private String keylanguagefull;
    private String keylanguage;
    private int img;

    public NationalFlag(String keylanguagefull, String keylanguage, int img) {
        this.keylanguagefull = keylanguagefull;
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

    public String getKeylanguagefull() {
        return keylanguagefull;
    }

    public void setKeylanguagefull(String keylanguagefull) {
        this.keylanguagefull = keylanguagefull;
    }
}
