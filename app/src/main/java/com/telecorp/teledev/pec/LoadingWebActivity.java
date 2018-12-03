package com.telecorp.teledev.pec;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoadingWebActivity extends ToolbarActivity {

    private WebView webView;
    private int userId;
    private String username;
    private Button next_wristband;
    private ProgressBar progressBar;
    private   String url;
    private Context mContext;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    Button btnSave1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading_web);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_loading_web, contentFrameLayout);
        setTitle("PEC");

        mContext = getApplicationContext();
        webView = findViewById(R.id.webView);
//        next_wristband= findViewById(R.id.next_wristband);
//        next_wristband.setOnClickListener(onClickNext);

        initWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = data == null || resultCode != LoadingWebActivity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initWebView() {

        SharedPreferences spGetDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
        userId = spGetDataUser.getInt("USER_ID", 0);
        username = spGetDataUser.getString("USERNAME", "");

        WebSettings webSettings =  webView.getSettings();
        webView.loadUrl("https://boxdoo.com/pcare/?os=android&userid=" + userId);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowContentAccess(true);
//        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }



        });
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








    }

    private void handleTelLink(String url) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // Send phone number to intent as data
        intent.setData(Uri.parse(url));
        // Start the dialer app activity with number
        startActivity(intent);
    }


    View.OnClickListener onClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoadingWebActivity.this, MainActivity_BluetoothFastBle.class);
            startActivity(intent);
        }
    };

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
