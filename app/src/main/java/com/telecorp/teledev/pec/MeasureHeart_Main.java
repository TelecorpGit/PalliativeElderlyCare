package com.telecorp.teledev.pec;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.github.ybq.android.spinkit.style.Wave;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;


public class MeasureHeart_Main extends ToolbarActivity   {

    private TransparentProgressDialog pd_Dialog;
    // private Handler h_Dialog;
    //private Runnable r_Dialog;
    private void SetDialog() {
        //h_Dialog = new Handler();
        pd_Dialog = new TransparentProgressDialog(this,R.mipmap.ic_loading);

    }
    @Override
    protected void onDestroy() {
        // h_Dialog.removeCallbacks(r_Dialog);
        if (pd_Dialog.isShowing() ) {
            pd_Dialog.dismiss();
        }
        super.onDestroy();
    }
    TextView txt_name,txt_mac,txt_rssi;
    ProgressBar progressBar;
    int cout_=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_h);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.fragment_h, contentFrameLayout);
        setTitle("PEC");
        //Generate list View from ArrayList


//        Toolbar toolbarS = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbarS);
        SetDialog();


//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       // final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_name_bacnk);
        //upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
       // getSupportActionBar().setHomeAsUpIndicator(upArrow);

        TextView settitle = (TextView) findViewById(R.id.settitle);
        settitle.setText("อัตราการเต้นของหัวใจ");
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Wave doubleWave = new Wave();
        progressBar.setIndeterminateDrawable(doubleWave);


        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_mac= (TextView) findViewById(R.id.txt_mac);
        txt_rssi= (TextView) findViewById(R.id.txt_rssi);


    }
    @Override
    public void onStart() {
        super.onStart();
        //File_Confix_Data.Mac

        cout_=0;
        BleManager.getInstance().notify(
                File_Confix_Data.bleDevice_login,
                File_Confix_Data.ServicegetUuid,
                File_Confix_Data.getUuid,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        Debug.out("notify success");

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //check_notify=true;
//
//
//                            }
//                        });
                    }

                    @Override
                    public void onNotifyFailure(final BleException exception) {
                        Debug.out("onNotifyFailure");
//                        if(check_b){
//                            Intent intent = new Intent(getActivity(), MainActivity_BluetoothFastBle.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                        }

                    }

                    @Override
                    public void onCharacteristicChanged(final byte[] data) {


                        // Debug.out(""+HexUtil.formatHexString(data , true));
                        String  datat = HexUtil.formatHexString(data , true);

                        String[] parts = datat.split(" ");

                        String  chip_code="";

                        if(parts.length==1){
                            Debug.out("1 "+chip_code);
                            chip_code= parts[0]     ;
                            String check_h=parts[0];
                            if( check_h.equals("e5")){

                            }else{

                                txt_name.setText("กรุณาปรับหน้า wristband");
                                txt_mac.setText("ให้อยู่หน้าวัดการเต้นของหัวใจ");
                                Toast.makeText(MeasureHeart_Main.this,"กรุณาปรับหน้า wristband ให้อยู่หน้าวัดการเต้นของหัวใจ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else if(parts.length==4){

                            String check_h=parts[0]+parts[1];
                            if( check_h.equals("e511")){
                                cout_++;
                                int ival = Integer.parseInt(parts[3], 16);
                                Debug.out( giveDate()+"  "+File_Confix_Data.Mac+"  "+ival);
                                txt_name.setText(" การเต้นของหัวใจ "+ival+" BMP");
                                txt_mac.setText(giveDate());
                                txt_rssi.setText("ครั้งที่ "+cout_+" ");
                                String count_="ครั้งที่ "+cout_+" ";
                                String date_=giveDate();
                                String time_data=giveTime();

                                SetTimSetdata();

                                if(time_save_data){
                                    time_save_data=false;
                                    SetChipCodeLatLon(   ival ,  File_Confix_Data.Mac,  date_ ,time_data,count_);
                                }

                                //   SetData_TechnicialJobList(ival);


                            }else{

                                Toast.makeText(MeasureHeart_Main.this,"กรุณาปรับหน้า wristband ให้อยู่หน้าวัดการเต้นของหัวใจ",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } else  {

                            Toast.makeText(MeasureHeart_Main.this,"กรุณาปรับหน้า wristband ให้อยู่หน้าวัดการเต้นของหัวใจ",
                                    Toast.LENGTH_SHORT).show();

                        }

                        //Debug.out(""+chip_code);
                    }


                });



    }
    boolean time_save_data=true;
    private void SetTimSetdata() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                time_save_data=true;

            }
        }, File_Confix_Data.delayMillis);
        //delayMillis
    }
    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EE d MMM yyyy");
        return sdf.format(cal.getTime());

    }
    public String giveTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());

    }
    @Override
    protected void onStop() {
        super.onStop();
        BleManager.getInstance().stopNotify(
                File_Confix_Data.bleDevice_login,
                File_Confix_Data.ServicegetUuid,
                File_Confix_Data.getUuid);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.

                BleManager.getInstance().stopNotify(
                        File_Confix_Data.bleDevice_login,
                        File_Confix_Data.ServicegetUuid,
                        File_Confix_Data.getUuid);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }


    private void  SetChipCodeLatLon(  int ival ,String  Mac,String date_ ,String time_data,String count_) {

        SharedPreferences sp = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "");
        String firstName = sp.getString("FirstName", "");
        String lastname = sp.getString("Lastname", "");

       String  user_detail= "MacID :" +Mac;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get( getString(R.string.url)+"/APITravel/AddCommenttravel?category_id="
                        +"27"
                        +"&user_name="+firstName +" "+ lastname
                        +"&user_detail="+user_detail
                        +"&data1="+ival
                        +"&data2="+ username
                        +"&data3="+time_data
                        +"&data4="+count_
                        +"&data5="+date_
//                        +"27"
//                        +"&user_name="+File_Confix_Data.data_user.getData().getFirst_name()+" "+File_Confix_Data.data_user.getData().getLast_name()
//                        +"&user_detail="+user_detail
//                        +"&data1="+ival
//                        +"&data2="+ File_Confix_Data.data_user.getData().getUsername()
//                        +"&data3="+time_data
//                        +"&data4="+count_
//                        +"&data5="+date_

                , new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        String str = null;
                        try {
                            str = new String(response, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Debug.out(str);



                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                        String str = "";
                        try {
                            str = new String(errorResponse, "UTF-8");
                        } catch ( Exception es) {
                            // TODO Auto-generated catch block
                            es.printStackTrace();
                        }

                        Debug.out(str);

                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried http://projectandroid.top/Travel
                    }
                });


    }


}