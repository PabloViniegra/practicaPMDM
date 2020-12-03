package com.example.practicapmdm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.practicapmdm.R;
import com.example.practicapmdm.constants.Constants;

public class WebViewActivity extends AppCompatActivity {
private Button btnTourism;
    private Button btnPool;
    private Button btnSportCenter;
    private Button btnSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        btnTourism=findViewById(R.id.BtnTourism);
        btnPool=findViewById(R.id.BtnPool);
        btnSportCenter=findViewById(R.id.BtnTourism);
        btnSport=findViewById(R.id.BtnMadridSport);

        btnTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView mWebView= findViewById(R.id.webView);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.loadUrl(Constants.LINKTOURISM);
            }
        });
        btnPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView mWebView= findViewById(R.id.webView);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.loadUrl(Constants.LINKPOOL);
            }
        });
        btnSportCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView mWebView= findViewById(R.id.webView);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.loadUrl(Constants.LINKSPORTCENTER);
            }
        });
        btnSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView mWebView= findViewById(R.id.webView);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.loadUrl(Constants.LINKSPORT);
            }
        });


    }
}