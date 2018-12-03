package com.telecorp.teledev.pec;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class ForgotPasswordActivity extends Toolbart{

    private WebView webViewForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_forgot_password, contentFrameLayout);
        setTitle("PEC");

        webViewForgot = findViewById(R.id.webView);
        webViewForgotPassword();
    }

    private void webViewForgotPassword() {

        webViewForgot.setWebViewClient(new WebViewClient());
        webViewForgot.loadUrl("http://boxdoo.com/pcare/register/forgotpass.php");

        WebSettings webSettings = webViewForgot.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }


    @Override
    public void onBackPressed() {
        if (webViewForgot.canGoBack()){
            webViewForgot.goBack();
            finish();
        }else {
            return;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webViewForgot.saveState(outState);
    }
}
