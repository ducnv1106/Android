package com.ducnv1106.message;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppBinding {

    @BindingAdapter("thumb")
    public static void setThumb(ImageView img, String imgURL) {
        if (imgURL.equals("default")) {
            Glide.with(img).load(R.mipmap.ic_launcher).into(img);
        } else {
            Glide.with(img).load(imgURL).into(img);
        }
    }

    @BindingAdapter("converImage")
    public static void setConverImage(ImageView img, String url) {
        if (url.equals("default")) {
            img.setImageResource(R.drawable.background_default);
        } else {
            Glide.with(img).load(url).into(img);
        }
    }

    @BindingAdapter(value = {"visibilityOnline", "isStatusOnline"})
    public static void updateStatusOnline(@NonNull ImageView imageView, @Nullable String status, @Nullable Boolean isStatus) {
        if (!isStatus) {
            imageView.setVisibility(View.GONE);
            return;
        }
        if (status.equals("offline")) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter(value = {"visibilityOffline", "isStatusOffline"})
    public static void updateStatusOffline(@NonNull ImageView imageView, @Nullable String status, @Nullable Boolean isStatus) {
        if (!isStatus) {
            imageView.setVisibility(View.GONE);
            return;
        }
        if (status.equals("offline")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"isFriend", "Friend"})
    public static void setFriend(ImageView img, Boolean isFriend, Boolean Friend) {
        if (!isFriend) {
            img.setVisibility(View.GONE);
            return;
        }

        if (Friend) {
            img.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @BindingAdapter(value = {"isMessage","tvMessage","tvUser"})
    public static void setMessage(TextView tv, Boolean isMessage,Message message,User user) {
        if (!isMessage) {
            tv.setVisibility(View.GONE);
            return;
        }

        if(message==null){
            return;
        }
        if(message.getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
           tv.setTextColor(R.color.colorPrimaryDark);
            if(message.getType().equals("text")){
                tv.setText("You: "+message.getMessage());
            }else {
                tv.setText("You: Send an image");
            }


            return;
        }

        if(message.getSender().equals(user.getUserId())){

            if(!message.getIsseen()){
                tv.setTextAppearance(R.style.TextAppearance_AppCompat);
                if(message.getType().equals("image")){
                    tv.setText("Send an image to you");
                }else {
                    tv.setText(message.getMessage());
                }

            }else {
                tv.setTextColor(R.color.colorPrimaryDark);
                if(message.getType().equals("image")){
                    tv.setText("Send an image to you");
                }else {
                    tv.setText(message.getMessage());
                }
            }
        }

    }


    @BindingAdapter(value = {"urlImg","user","message","isMessage"})
    public static void checkSeen(CircleImageView img, String urlImg, User user,Message message,Boolean isMessage) {
        if(!isMessage){
            img.setVisibility(View.GONE);
            return;
        }
        if (message == null) {
            return;
        }
        if(message.getSender().equals(user.getUserId())){
            img.setVisibility(View.INVISIBLE);
            return;
        }
        if (message.getIsseen()) {
            img.setVisibility(View.VISIBLE);
            Glide.with(img).load(urlImg).into(img);

        } else if (message.getIsseen() == false) {
            img.setVisibility(View.VISIBLE);
            img.setImageResource(R.drawable.ic_check_send_circle);
        }

    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView img,String url){

        if(url!=null){
            Glide.with(img).load(url).into(img);
        }

    }

    @BindingAdapter("text")
    public static void setText(TextView tv, String gender) {

        if (gender.equals("default")) {
            tv.setText("Male");
        } else {
            tv.setText(gender);
        }

    }

    @BindingAdapter("birthday")
    public static void setBirthday(TextView tv, String birthday) {
        if (birthday.equals("default")) {
            tv.setText("21-06-1999");
        } else {
            tv.setText(birthday);
        }
    }

    @BindingAdapter("country")
    public static void setCountry(TextView tv, String country) {

        if (country.equals("default")) {
            tv.setText("VietNam");
        } else {
            tv.setText(country);
        }
    }

    @BindingAdapter("interestedin")
    public static void setInterestedIn(TextView tv, String interestedIn) {
        if (interestedIn.equals("default")) {
            tv.setText("Women");
        } else {
            tv.setText(interestedIn);
        }
    }

    @BindingAdapter("address")
    public static void setAddress(TextView tv, String email) {
        if (email.equals("default")) {
            tv.setText("");
        } else {
            tv.setText(email);
        }
    }



}
