package com.telecorp.teledev.pec;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class ForumsActivity extends ToolbarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forums);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_forums, contentFrameLayout);
        setTitle("PEC");

        webView = findViewById(R.id.webView);

        initWebView();
    }

    private void initWebView() {

        SharedPreferences spGetDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
        int userId = spGetDataUser.getInt("USER_ID", 0);
        String username = spGetDataUser.getString("USERNAME", "");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://boxdoo.com/pcare/forum/public/index.php?userid" + userId);
        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            return;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        // Save the state of the WebView
        webView.saveState(outState);
    }
}
