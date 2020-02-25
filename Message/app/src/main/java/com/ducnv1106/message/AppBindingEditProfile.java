package com.ducnv1106.message;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class AppBindingEditProfile {

    @BindingAdapter("dateofbirth")
    public  static void setDateOfBirth(TextView tv,String birthday){
        if(birthday.equals("default")){
            tv.setText("21");
        }else {
            int index = birthday.indexOf("-");
            String date = birthday.substring(0,index);
            tv.setText(date);

        }
    }

    @BindingAdapter("monthofbirth")
    public static void setMonthOfBirth(TextView tv,String birthday){
        if(birthday.equals("Default")){
            tv.setText("06");
        }else {
            int index = birthday.indexOf("-");

            if(index!=-1){
                int index1 = birthday.indexOf("-",index+1);
                String month = birthday.substring(index+1,index1);
                tv.setText(month);
            }
        }
    }
    @BindingAdapter("yearofbirth")
    public static void setYearOfBirth(TextView tv,String birthday){
        if(birthday.equals("default")){
            tv.setText("1999");
        }else {
            int index = birthday.indexOf("-");
            if(index!=-1){
                index = birthday.indexOf("-",index+1);
                String year = birthday.substring(index+1);
                tv.setText(year);
            }
        }
    }

    @BindingAdapter("genderMale")
    public static void setGenderMale(TextView tv,String gender){
        if(gender.equals("default") || gender.equals("Male")){
            tv.setVisibility(View.VISIBLE);
        }else {
            tv.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("genderFeMale")
    public static void setGenderFeMale(TextView tv,String gender){
        if(gender.equals("FeMale")){
            tv.setVisibility(View.VISIBLE);
        }else {
            tv.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("genderOther")
    public static void setGenderOther(TextView tv,String gender){
        if(gender.equals("Other")){
            tv.setVisibility(View.VISIBLE);
        }else {
            tv.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("country")
    public static void setCountry(EditText edt,String country){
        if(country.equals("default")){
            edt.setText("");
        }else {
            edt.setText(country);
        }
    }
    @BindingAdapter("interestedInWomen")
    public static void setInterestedIn(TextView tv,String interestedIn){
        int index = interestedIn.indexOf("Women");
        if(interestedIn.equals("defalt") || index!=-1){
            tv.setVisibility(View.VISIBLE);
        }else {
            tv.setVisibility(View.GONE);
        }
    }
    @BindingAdapter("interestedInMen")
    public static void setInterestedInMen(TextView tv,String interestedIn){
        int index = interestedIn.indexOf("Men");
        if(index!=-1){
            tv.setVisibility(View.VISIBLE);
        }else {
            tv.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("religious")
    public static void setReligious(EditText edt,String religious){
        if (religious.equals("default")){
            edt.setText("");
        }else {
            edt.setText(religious);
        }
    }

    @BindingAdapter("political")
    public static void serPolitical(EditText edt,String political){
        if(political.equals("default")){
            edt.setText("");
        }else {
            edt.setText(political);
        }
    }
}
