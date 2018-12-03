package com.telecorp.teledev.pec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class RegisAdminActivity extends ToolbarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regis_admin);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_regis_admin, contentFrameLayout);
        setTitle("PEC");

        webView = findViewById(R.id.webView);

        initWebView();
    }

    private void initWebView() {

        SharedPreferences spGetDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
        int userId = spGetDataUser.getInt("USER_ID", 0);
        String username = spGetDataUser.getString("USERNAME", "");

        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){

                if(url.startsWith("http")){
//                    Toast.makeText(mContext,"Page link",Toast.LENGTH_SHORT).show();
                    // Return false means, web view will handle the link
                    return false;
                }else if(url.startsWith("tel:")){
                    // Handle the tel: link
                    handleTelLink(url);

                    // Return true means, leave the current web view and handle the url itself
                    return true;
                }

                return false;
            }


            // From api level 24
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
//                Toast.makeText(mContext, "New Method",Toast.LENGTH_SHORT).show();

                // Get the tel: url
                String url = request.getUrl().toString();

                if(url.startsWith("http")){
//                    Toast.makeText(mContext,"Page link",Toast.LENGTH_SHORT).show();
                    // Return false means, web view will handle the link
                    return false;
                }else if(url.startsWith("tel:")){
                    // Handle the tel: link
                    handleTelLink(url);

                    // Return true means, leave the current web view and handle the url itself
                    return true;
                }

                return false;
            }
        });


        webView.loadUrl("https://boxdoo.com/pcare/addfriend/index.php?userid=" + userId);


    }

    private void handleTelLink(String url) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Send phone number to intent as data
        intent.setData(Uri.parse(url));
        // Start the dialer app activity with number
        startActivity(intent);
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
