package com.telecorp.teledev.pec;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;

import java.util.ArrayList;
import java.util.List;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends Activity {
    private final String TAG = getClass().getSimpleName();
    //rivate TextView tvTxtActive;

    private void requestLogin() {

        finish();

        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_splashscreen);
//        String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
//        int sdkVersion = android.os.Build.VERSION.SDK_INT; // e.g. sdkVersion := 8;
//
//        Debug.out("myVersion "+myVersion);
//        Debug.out("sdkVersion "+sdkVersion);
//


        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        File_Confix_Data.manufacturer=manufacturer;
        File_Confix_Data.model=model;
        File_Confix_Data.version_s=version;
        File_Confix_Data.versionRelease=versionRelease;

//        manufacturer samsung
//        model SM-J600G
//        version 26
//        versionRelease 8.0.0


        BleManager.getInstance().init(getApplication());
//        BleManager.getInstance().enableLog(true).setReConnectCount(1, 5000).setConnectOverTime(20000).setOperateTimeout(5000);


        File_Confix_Data.getUuid="000033f2-0000-1000-8000-00805f9b34fb";
        File_Confix_Data.ServicegetUuid="000055ff-0000-1000-8000-00805f9b34fb";






//        SharedPreferences wifiusername = getSharedPreferences("WIFIUSRNAME", 0);
//        File_Confix_Data.wifiusernameS = wifiusername.getString("wifiusername", "");
//
//
//
//        SharedPreferences wifipassword = getSharedPreferences("WIFIPASSWORD", 0);
//        File_Confix_Data.wifipasswordS = wifipassword.getString("wifipassword", "");



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

        // checkVersion();


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

        //BleManager.getInstance().disconnectAllDevice();
        // BleManager.getInstance().destroy();



//        if(isOnline()){
//     VersionChecker versionChecker = new VersionChecker();
//        try {
//            String latestVersion = versionChecker.execute().get();
//            String versionName = BuildConfig.VERSION_NAME;
//            Debug.out("latestVersion "+ latestVersion);
//            Debug.out("versionName "+ versionName);
//
//            if (latestVersion != null && !latestVersion.isEmpty()) {
//                if (!latestVersion.equals(versionName)) {
//
//                    popupUpdateVersion(versionName);
//                }else {
//                    Debug.out("latestVersion "+ latestVersion);
//                    Debug.out("versionName "+ versionName);
//                    reqActive();
//                }
//            }else{
//                Debug.out("latestVersion "+ latestVersion);
//                Debug.out("versionName "+ versionName);
//                reqActive();
//            }
//
//
//            //   Log.d("update", "Current version " + Float.valueOf(versionName) + ", Playstore version " + Float.valueOf(latestVersion));
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        }else{
//            checkPermissions();
//            reqActive();
//        }
        checkPermissions();
        reqActive();
    }

    public void reqActive() {
        Debug.out("isOnline "+isOnline());

        SharedPreferences database_list = getSharedPreferences("LOGINUSER", 0);
        String loginuser = database_list.getString("loginuser", "");

        if(loginuser.equals("true")){

            SharedPreferences profileSignin = getSharedPreferences("MACADDREES", 0);
            String mac = profileSignin.getString("mac", "");
            File_Confix_Data.Mac=mac;

//              GetConnectBluetooth(mac);
            Intent intent = new Intent(SplashScreenActivity.this, LoadingWebActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else{
            requestLogin();
        }
    }

//

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BleManager.getInstance().disconnectAllDevice();
//        BleManager.getInstance().destroy();
//    }


    private void GetConnectBluetooth(String mac) {
        if(mac.equals("-")||mac.equals("" )||mac==null ){
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
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

                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
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

                    Intent intent = new Intent(SplashScreenActivity.this, GridViewExampleActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {

                }
            });
        }





    }



    private void popupUpdateVersion(final String version) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                builder.setTitle("อัปเดตเวอร์ชั่น");
                builder.setCancelable(false);
                builder.setMessage("เวอร์ชั่นใหม่ของ " + getString(R.string.app_name) + " พร้อมใช้งานได้โปรดอัปเดตเป็นเวอร์ชั่น " + version + " ตอนนี้");
                builder.setPositiveButton("อัปเดต", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                        }
                    }
                });
                builder.create().show();
            }
        });

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



    private static final int REQUEST_CODE_OPEN_GPS = 1;
}