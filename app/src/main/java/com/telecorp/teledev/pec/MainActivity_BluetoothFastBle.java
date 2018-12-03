package com.telecorp.teledev.pec;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleMtuChangedCallback;
import com.clj.fastble.callback.BleRssiCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.telecorp.teledev.pec.adapter.DeviceAdapter_BluetoothFastBle;
import com.telecorp.teledev.pec.adapter.ObserverManager_BluetoothFastBle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity_BluetoothFastBle extends ToolbarActivity implements View.OnClickListener   {


    private static final String TAG = MainActivity_BluetoothFastBle.class.getSimpleName();
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private Button scan_bluetooth;
    private ImageView img_loading;
    private Animation operatingAnim;
    private DeviceAdapter_BluetoothFastBle mDeviceAdapter;
    private ProgressDialog progressDialog;
    private  TextView search_bluetooth;
    private  double latitude =13.735986;
    private double longitude=100.6407271;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mainbluetoothble);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_mainbluetoothble, contentFrameLayout);
        setTitle("PEC");


        initView();

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance().enableLog(true).setReConnectCount(1, 5000).setConnectOverTime(20000).setOperateTimeout(5000);

        SetDialog();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (pd_Dialog.isShowing() ) {
            pd_Dialog.dismiss();
        }
        Debug.out("onDestroy");
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_bluetooth:
                if (scan_bluetooth.getText().equals("ค้นหา")) {
                    checkPermissions();
                } else if (scan_bluetooth.getText().equals("หยุด")) {
                    BleManager.getInstance().cancelScan();
                }
                break;

        }
    }

    private void initView() {

//        Toolbar toolbarS = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbarS);
//
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_name_bacnk);
        //upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
       // getSupportActionBar().setHomeAsUpIndicator(upArrow);


        TextView settitle = (TextView) findViewById(R.id.settitle);
        settitle.setText("การเชื่อมต่ออุปกรณ์");
        scan_bluetooth = (Button) findViewById(R.id.scan_bluetooth);
        scan_bluetooth.setText(getString(R.string.start_scan));
        scan_bluetooth.setOnClickListener(this);
        bluetooth_connext = (LinearLayout) findViewById(R.id.bluetooth_connext);

        search_bluetooth = (TextView) findViewById(R.id.search_bluetooth);
        img_loading = (ImageView) findViewById(R.id.img_loading);
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        progressDialog = new ProgressDialog(this);

        mDeviceAdapter = new DeviceAdapter_BluetoothFastBle(this);
        mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter_BluetoothFastBle.OnDeviceClickListener() {
            @Override
            public void onConnect(BleDevice bleDevice) {
                if (!BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().cancelScan();
                    connect(bleDevice);
                }
            }

            @Override
            public void onDisConnect(final BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                    File_Confix_Data.Mac="-";
                }
            }

            @Override
            public void onDetail(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {

                }
            }
        });
        ListView listView_device = (ListView) findViewById(R.id.list_device);
        listView_device.setAdapter(mDeviceAdapter);
    }

    private void showConnectedDevice() {
        List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();
        mDeviceAdapter.clearConnectedDevice();

      boolean check_add_layout_=true;
        for (int i=0;i<deviceList.size();i++) {
            boolean isConnected = BleManager.getInstance().isConnected(deviceList.get(i));
            Debug.out("name bluetooth "+deviceList.get(i).getName());
            if(isConnected){
                check_add_layout_=false;
                Add_ConnectBluetooth(deviceList.get(i));
            }

            mDeviceAdapter.addDevice(deviceList.get(i));
        }

        mDeviceAdapter.notifyDataSetChanged();

        if(check_add_layout_){
            Add_Connect_NoConnect();
        }
    }



    private void setScanRule() {
       // serviceUuids          UUID getUuid = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
//        UUID RX_CHAR_UUID = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
//        UUID getUuid[] ={UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"),RX_CHAR_UUID}; ;
//
//        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
//                .setServiceUuids(getUuid)      // 只扫描指定的服务的设备，可选
//                .setDeviceName(true, File_Confix_Data.bleDevicegetName)   // 只扫描指定广播名的设备，可选
//                .setDeviceMac(File_Confix_Data.Mac)                  // 只扫描指定mac的设备，可选
//                .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
//                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
//                .build();
//        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    private void startScan() {

        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
//                mDeviceAdapter.clearScanDevice();
                mDeviceAdapter.notifyDataSetChanged();
                img_loading.startAnimation(operatingAnim);
                img_loading.setVisibility(View.VISIBLE);
                search_bluetooth.setVisibility(View.VISIBLE);
                scan_bluetooth.setText(getString(R.string.stop_scan));
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                mDeviceAdapter.addDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();

                    if(File_Confix_Data.Mac.equals(bleDevice.getMac())){
                        File_Confix_Data.bleDevice_login=bleDevice;
//                        Intent myIntent = new Intent(MainActivity_BluetoothFastBle.this, GridViewExampleActivity.class);
//                        startActivity(myIntent);

                    }

            }


            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {

                for(int i=0;i<scanResultList.size();i++){
                    Debug.out("scanResultList "+scanResultList.get(i).getMac());

                    if(File_Confix_Data.Mac.equals(scanResultList.get(i).getMac())){
                        File_Confix_Data.bleDevice_login=scanResultList.get(i);
                       // File_Confix_Data.bleDevice_login.getDevice().getUuids()

//                        Intent myIntent = new Intent(MainActivity_BluetoothFastBle.this, GridViewExampleActivity.class);
//                        startActivity(myIntent);
                    }
                }

                img_loading.clearAnimation();
                img_loading.setVisibility(View.GONE);
                search_bluetooth.setVisibility(View.GONE);
                scan_bluetooth.setText(getString(R.string.start_scan));
            }
        });
    }

    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {

                progressDialog.show();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                img_loading.clearAnimation();
                img_loading.setVisibility(View.GONE);
                search_bluetooth.setVisibility(View.GONE);
                scan_bluetooth.setText(getString(R.string.start_scan));
                File_Confix_Data.Mac="-";

                progressDialog.dismiss();
                Toast.makeText(MainActivity_BluetoothFastBle.this, getString(R.string.connect_fail), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                mDeviceAdapter.addDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();
                Add_ConnectBluetooth(bleDevice);
                File_Confix_Data.Mac=bleDevice.getMac();
                File_Confix_Data.bleDevicegetName=bleDevice.getName();
                File_Confix_Data.bleDevice_login= bleDevice;

                SharedPreferences Categorysettings = getSharedPreferences("MACADDREES", 0);
                SharedPreferences.Editor prefsEdCategory = Categorysettings.edit();
                prefsEdCategory.putString("mac",  File_Confix_Data.Mac);
                prefsEdCategory.commit();

                SharedPreferences LOGINUSER = getSharedPreferences("LOGINUSER", 0);
                SharedPreferences.Editor loginuserss = LOGINUSER.edit();
                loginuserss.putString("loginuser",  "true");
                loginuserss.commit();

                Intent myIntent = new Intent(MainActivity_BluetoothFastBle.this, GridViewExampleActivity.class);
                    MainActivity_BluetoothFastBle.this.startActivity(myIntent);

            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                File_Confix_Data.Mac="-";
                mDeviceAdapter.removeDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
                    Toast.makeText(MainActivity_BluetoothFastBle.this, getString(R.string.active_disconnected), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity_BluetoothFastBle.this, CheckWristbandActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity_BluetoothFastBle.this, getString(R.string.disconnected), Toast.LENGTH_LONG).show();
                    ObserverManager_BluetoothFastBle.getInstance().notifyObserver(bleDevice);
                    Intent intent = new Intent(MainActivity_BluetoothFastBle.this, CheckWristbandActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void readRssi(BleDevice bleDevice) {
        BleManager.getInstance().readRssi(bleDevice, new BleRssiCallback() {
            @Override
            public void onRssiFailure(BleException exception) {
                Log.i(TAG, "onRssiFailure" + exception.toString());
            }

            @Override
            public void onRssiSuccess(int rssi) {
                Log.i(TAG, "onRssiSuccess: " + rssi);
            }
        });
    }

    private void setMtu(BleDevice bleDevice, int mtu) {
        BleManager.getInstance().setMtu(bleDevice, mtu, new BleMtuChangedCallback() {
            @Override
            public void onSetMTUFailure(BleException exception) {
                Log.i(TAG, "onsetMTUFailure" + exception.toString());
            }

            @Override
            public void onMtuChanged(int mtu) {
                Log.i(TAG, "onMtuChanged: " + mtu);
            }
        });
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }

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
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifyTitle)
                            .setMessage(R.string.gpsNotifyMsg)
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
                    setScanRule();
                    startScan();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                setScanRule();
                startScan();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkPermissions();

        startScan();
        //showLast();
    }



    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    @Override
    protected void onResume() {
        super.onResume();

        startScan();
        showConnectedDevice();
        if (ContextCompat.checkSelfPermission(MainActivity_BluetoothFastBle.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity_BluetoothFastBle.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
            return;
        }
//        SmartLocation.with(this)
//                .location()
//                .config(LocationParams.BEST_EFFORT)
//                .start((OnLocationUpdatedListener) this);
    }
    private static final int LOCATION_PERMISSION_ID = 1001;
    @Override
    public void onBackPressed() {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
    private void Add_ConnectBluetooth(final BleDevice bleDevice) {
        bluetooth_connext.removeAllViews();
        mInflater = LayoutInflater.from(MainActivity_BluetoothFastBle.this);
        View convertView = mInflater.inflate(R.layout.setting_bluetooth_list_layou, bluetooth_connext, false);
        bluetooth_connext.addView(convertView);

        Button btn_bluetooth;
        TextView name_s3_hybrid;

        name_s3_hybrid = (TextView) convertView.findViewById(R.id.name_s3_hybrid);
        btn_bluetooth= (Button) convertView.findViewById(R.id.btn_bluetooth);
        btn_bluetooth.setText("ยกเลิก");
        btn_bluetooth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                File_Confix_Data.Mac="-";

                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                }
                bluetooth_connext.removeAllViews();
                showConnectedDevice();
                startScan();
                mDeviceAdapter.notifyDataSetChanged();
            }
        });
        name_s3_hybrid.setText(bleDevice.getName()+" "+" "+bleDevice.getMac());

    }


    private LinearLayout bluetooth_connext;
    private LayoutInflater mInflater;
    private void Add_Connect_NoConnect() {
        bluetooth_connext.removeAllViews();
        mInflater = LayoutInflater.from(MainActivity_BluetoothFastBle.this);
        View convertView = mInflater.inflate(R.layout.setting_bluetooth_list_layou, bluetooth_connext, false);
        bluetooth_connext.addView(convertView);

        Button btn_bluetooth;
        TextView name_s3_hybrid;

        name_s3_hybrid = (TextView) convertView.findViewById(R.id.name_s3_hybrid);
        btn_bluetooth= (Button) convertView.findViewById(R.id.btn_bluetooth);
        btn_bluetooth.setVisibility(View.GONE);
        name_s3_hybrid.setText("ไม่มีการเชื่อมต่ออุปกรณ์");

    }




    private TransparentProgressDialog pd_Dialog;

    private void SetDialog() {

        pd_Dialog = new TransparentProgressDialog(this, R.mipmap.ic_loading);

    }







    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  locationUtility.stopLocationUpdates();
    }

}
