package com.phongbm.musicplayer.views;

import android.app.Dialog;
import android.content.Context;

import com.phongbm.musicplayer.R;

public class MP3ProgressDialog extends Dialog {

    public MP3ProgressDialog(Context context) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.progress_dialog);
        setCancelable(false);
    }
}
