package com.telecorp.teledev.pec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MainLogin extends AppCompatActivity   {

    private   Button b1  ;
    private   EditText ed1,ed2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

//        ed1.setText("nut4");
//
//        ed2.setText("1234");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if(ed1.getText().toString().length()>1&&
                ed2.getText().toString().length()>1){

//                            if(ed1.getText().toString().equals("admin") &&
//                                    ed2.getText().toString().equals("admin")) {

                                username(ed1.getText().toString(),ed2.getText().toString() );



                        }else {

                        }


            }
        });


    }

    private void  username( String username,String password ) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username,password);
       // client.get("https://example.com");

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.setBasicAuth(username,password, new AuthScope("27.254.67.218:8080/pec/rest/getUserInfo", 80, AuthScope.ANY_REALM));

        client.get("http://27.254.67.218:8080/pec/rest/getUserInfo", new AsyncHttpResponseHandler() {

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
                } catch ( Exception ess) {
                    // TODO Auto-generated catch block
                    ess.printStackTrace();
                }


                Gson gson = new Gson();
                UserData getUser=   gson.fromJson(data_return.toString(), UserData.class);
                File_Confix_Data.data_user=getUser;


                Intent intent = new Intent(MainLogin.this, MainActivity_BluetoothFastBle.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)


                String str = null;
                try {
                    str = new String(errorResponse, "UTF-8");
                } catch ( Exception ess) {
                    // TODO Auto-generated catch block
                    ess.printStackTrace();
                }
                Debug.out("M_Location "+str);
                Debug.out("onFailure");
                Toast.makeText(getApplicationContext(),
                        "username or password ไม่ถูกต้อง...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });




    }
}