package com.telecorp.teledev.pec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity {


    private Button btnLogin;
    private EditText username;
    private ShowHidePasswordEditText password;
    private TextView register;
    private TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnLogin = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password =  findViewById(R.id.android_hide_show_edittext_password);
        forgotPass = findViewById(R.id.forgot_password);
        register = findViewById(R.id.clickRegister);

//        ed1.setText("nut4");
//
//        ed2.setText("1234");
        btnLogin.setOnClickListener(onClickLogin);


    }

    public void onClickRegister(View view){
        Intent registerI = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerI);
    }

    public void onClickForgot(View view){
        Intent forgotI = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(forgotI);
    }


    View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (username.getText().toString().length() > 1 &&
                    password.getText().toString().length() > 1) {

//                            if(ed1.getText().toString().equals("admin") &&
//                                    ed2.getText().toString().equals("admin")) {

                Login(username.getText().toString(), password.getText().toString());

            } else {
                Toast.makeText(LoginActivity.this, "กรุณากรอก ชื่อผู้ใช้งาน และ รหัสผ่าน", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void Login(final String username, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://boxdoo.com/pcare/api/login.php?username=" +username +"&password="+ password , new AsyncHttpResponseHandler(){

            @Override
            public void onStart() {
                // called before request is started
                Debug.out("onStart");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Debug.out("onSuccess");

                String data_return = null;
                try {
                    data_return = new String(response, "UTF-8");
                } catch (Exception ess) {
                    // TODO Auto-generated catch block
                    ess.printStackTrace();
                }


                Gson gson = new Gson();
                UserData getUser = gson.fromJson(data_return.toString(), UserData.class);
                File_Confix_Data.data_user = getUser;
                if (getUser.getSuccess() == true){
                    int userId = getUser.getData().getUserid();
                    String username = getUser.getData().getUsername();
                    String FirstName = getUser.getData().getFirst_name();
                    String Lastname = getUser.getData().getLast_name();

//                    if (userId > 0) {

                    SharedPreferences sp = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("USER_ID", userId);
                    editor.putString("USERNAME", username);
                    editor.putString("FirstName", FirstName);
                    editor.putString("Lastname", Lastname);
                    editor.commit();

//                Intent intent = new Intent(MainActivity.this, MainActivity_BluetoothFastBle.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

                    Intent intent = new Intent(LoginActivity.this, LoadingWebActivity.class);
                    startActivity(intent);
//                }


                }else {
                    Toast.makeText(getApplicationContext(),
                            "ชื่อผู้ใช้งาน หรือ รหัสผ่าน ไม่ถูกต้อง...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)


                String str = null;
                try {
                    str = new String(errorResponse, "UTF-8");
                } catch (Exception ess) {
                    // TODO Auto-generated catch block
                    ess.printStackTrace();
                }
                Debug.out("M_Location " + str);
                Debug.out("onFailure");
                Toast.makeText(getApplicationContext(),
                        "ชื่อผู้ใช้งาน หรือ รหัสผ่าน ไม่ถูกต้อง...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }


//    private void Login(String username, String password) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.setBasicAuth(username, password);
//
//        // client.get("https://example.com");
//
////        AsyncHttpClient client = new AsyncHttpClient();
////        client.setBasicAuth(username,password, new AuthScope("27.254.67.218:8080/pec/rest/getUserInfo", 80, AuthScope.ANY_REALM));
//
////        client.get("http://27.254.67.218:8080/pec/rest/getUserInfo", new AsyncHttpResponseHandler() {
//        client.get("http://boxdoo.com/pcare/api/login.php?username=" +username +"&password="+ password +" ", new AsyncHttpResponseHandler() {
//
//
//            @Override
//            public void onStart() {
//                // called before request is started
//                Debug.out("onStart");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//                Debug.out("onSuccess");
//
//                    String data_return = null;
//                    try {
//                        data_return = new String(response, "UTF-8");
//                    } catch (Exception ess) {
//                        // TODO Auto-generated catch block
//                        ess.printStackTrace();
//                    }
//
//
//                    Gson gson = new Gson();
//                    UserData getUser = gson.fromJson(data_return.toString(), UserData.class);
//                File_Confix_Data.data_user = getUser;
//
////                Intent intent = new Intent(MainActivity.this, MainActivity_BluetoothFastBle.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(intent);
//
//                    Intent intent = new Intent(LoginActivity.this, LoadingWebActivity.class);
//
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//
//
//                String str = null;
//                try {
//                    str = new String(errorResponse, "UTF-8");
//                } catch (Exception ess) {
//                    // TODO Auto-generated catch block
//                    ess.printStackTrace();
//                }
//                Debug.out("M_Location " + str);
//                Debug.out("onFailure");
//                Toast.makeText(getApplicationContext(),
//                        "username or password ไม่ถูกต้อง...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
