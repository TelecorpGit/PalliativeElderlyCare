package com.telecorp.teledev.pec;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.List;

public class CheckWristbandActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    ProgressDialog pDialog;
    Handler handler;

//    private TextView tvTxtActive;

//    private void requestLogin() {
//
//        finish();
//
//        Intent intent = new Intent(CheckWristbandActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_check);


//        Intent intent = new Intent(this, MainActivity_BluetoothFastBle.class);
//        startActivity(intent);
//
//        finish();
//
//        loadingDialog = ProgressDialog.show(CheckWristbandActivity.this,"กำลังดำเนินการ...",
//                "รอสักครู่...", false, false);


        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        File_Confix_Data.manufacturer=manufacturer;
        File_Confix_Data.model=model;
        File_Confix_Data.version_s=version;
        File_Confix_Data.versionRelease=versionRelease;

        BleManager.getInstance().init(getApplication());

        File_Confix_Data.getUuid="000033f2-0000-1000-8000-00805f9b34fb";
        File_Confix_Data.ServicegetUuid="000055ff-0000-1000-8000-00805f9b34fb";


        showProgressDialog();

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // ซ่อน Progress Dialog
//                loadingDialog.dismiss();
//
//            }
//        }, 3000); // 3000 milliseconds delay

    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(CheckWristbandActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);



    }
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            // this.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CODE_BLUETOOTH_ON);
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkPermissions();
        reqActive();
    }

    public void reqActive() {
        Debug.out("isOnline "+isOnline());

        SharedPreferences database_list = getSharedPreferences("LOGINUSER", 0);
        String loginuser = database_list.getString("loginuser", "");

//        if(loginuser.equals("true")){

        SharedPreferences profileSignin = getSharedPreferences("MACADDREES", 0);
        String mac = profileSignin.getString("mac", "");
        File_Confix_Data.Mac=mac;

        GetConnectBluetooth(mac);
//            Intent intent = new Intent(CheckWristbandActivity.this, MainActivity_BluetoothFastBle.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);

//        }else{
//            requestLogin();
//        }
    }


    private void GetConnectBluetooth(String mac) {
        if(mac.equals("-")||mac.equals("" )||mac==null ){
            Intent intent = new Intent(CheckWristbandActivity.this, MainActivity_BluetoothFastBle.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{

            Debug.out(""+mac);
            BleManager.getInstance().connect(mac, new BleGattCallback() {
                @Override
                public void onStartConnect() {

                }

                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {

                    Intent intent = new Intent(CheckWristbandActivity.this, MainActivity_BluetoothFastBle.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    File_Confix_Data.bleDevice_login=bleDevice;
                    File_Confix_Data.bleDevicegetName=bleDevice.getName();
                    File_Confix_Data.Mac=bleDevice.getMac();

                    Intent intent = new Intent(CheckWristbandActivity.this, GridViewExampleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    Intent intent = new Intent(CheckWristbandActivity.this, MainActivity_BluetoothFastBle.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }



    public android.support.v7.app.AlertDialog.Builder buildDialog(Context c) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (pd_Dialog.isShowing() ) {
//            pd_Dialog.dismiss();
//        }

//    }


    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }


    private static final int REQUEST_CODE_OPEN_GPS = 1;
}
