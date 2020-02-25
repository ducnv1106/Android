package com.ducnv1106.message;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class AppBindingEditContact {

    @BindingAdapter("address")
    public static void setAddress(EditText edt,String address){
        if(address.equals("default")){
            edt.setText("");
        }else {
            edt.setText(address);
        }
    }
}
