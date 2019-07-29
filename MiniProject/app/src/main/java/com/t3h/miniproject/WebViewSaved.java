package com.t3h.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.t3h.miniproject.model.Constant;

public class WebViewSaved extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_save);
        webView=findViewById(R.id.webview_saved);
        webView.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        String path=intent.getStringExtra(Constant.EXTRA_URL);
        webView.loadUrl("file://"+path);
    }
}
