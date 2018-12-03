package com.telecorp.teledev.pec;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class RegisterActivity extends Toolbart {

    private WebView webViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_register, contentFrameLayout);
        setTitle("PEC");
        webViewRegister = findViewById(R.id.webViewRegister);

        WebViewRegister();

//        Toolbar toolbarS = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbarS);
//
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void WebViewRegister() {
        webViewRegister.setWebViewClient(new WebViewClient());
        webViewRegister.loadUrl("https://boxdoo.com/pcare/register/");

        WebSettings webSettings = webViewRegister.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewRegister.setWebViewClient(new WebViewClient());
    }


//    @Override
//    public void onBackPressed() {
//        if (webViewRegister.canGoBack()) {
//            webViewRegister.goBack();
//        } else {
//            return;
//        }
//    }


    @Override
    public void onBackPressed() {
        if (webViewRegister.canGoBack()) {
            webViewRegister.goBack();
            finish();
        } else {
            return;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        // Save the state of the WebView
        webViewRegister.saveState(outState);
    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        // Save the state of the WebView
//        webViewRegister.saveState(outState);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_favorite) {
////            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
////            return true;
////        }
//
//        switch (id) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }





}
