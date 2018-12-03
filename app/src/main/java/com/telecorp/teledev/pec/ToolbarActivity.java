package com.telecorp.teledev.pec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.telecorp.teledev.pec.adapter.DeviceAdapter_BluetoothFastBle;

public class ToolbarActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private Dialog dialog;
    private DeviceAdapter_BluetoothFastBle mDeviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        actionBar();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        noinspection SimplifiableIfStatement
        if (id == R.id.action_blood_pressure) {

            SharedPreferences sp = getSharedPreferences("LOGINUSER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("loginuser", "true");
            editor.commit();

            Intent intent = new Intent(ToolbarActivity.this, CheckWristbandActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(ToolbarActivity.this, LoadingWebActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_wristband ){
            SharedPreferences sp = getSharedPreferences("LOGINUSER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("loginuser", "true");
            editor.commit();

//            SharedPreferences profileSignin = getSharedPreferences("MACADDREES", 0);
//            String mac = profileSignin.getString("mac", "");
//
//            if (mac.equals("-")||mac.equals("" )||mac==null ){
                Intent intent = new Intent(ToolbarActivity.this, CheckWristbandActivity.class);
                startActivity(intent);
//            }else {
//                Intent intent = new Intent(ToolbarActivity.this, GridViewExampleActivity.class);
//                startActivity(intent);
//            }

        }else if (id == R.id.nav_regisadmin) {

            Intent intent = new Intent(ToolbarActivity.this, RegisAdminActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_forums) {

            Intent intent = new Intent(ToolbarActivity.this, ForumsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ToolbarActivity.this);
                builder.setMessage("คุณต้องการออกจากระบบใช่หรือไม่? ");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //show login form
                        Intent i = new Intent(ToolbarActivity.this, LoginActivity.class);
                        startActivity(i);

                        //clear ค่าใน SharedPreferences เมื่อทำการออกจากระบบ
                        SharedPreferences spDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edDataUser= spDataUser.edit();
                        edDataUser.clear();
                        edDataUser.commit();


                        SharedPreferences Categorysettings = getSharedPreferences("MACADDREES", 0);
                        SharedPreferences.Editor prefsEdCategory = Categorysettings.edit();
                        prefsEdCategory.clear();
                        prefsEdCategory.commit();

                        SharedPreferences LOGINUSER = getSharedPreferences("LOGINUSER", 0);
                        SharedPreferences.Editor loginuserss = LOGINUSER.edit();
                        loginuserss.clear();
                        loginuserss.commit();





                    }
                });
                builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id){
//            case android.R.id.home:
//                finish();
//                return true;
//            case R.id.action_settings:
//                AlertDialog.Builder builder = new AlertDialog.Builder(ToolbarActivity.this);
//                builder.setMessage("คุณต้องการออกจากระบบใช่หรือไม่? ");
//                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //show login form
//                        Intent i = new Intent(ToolbarActivity.this, LoginActivity.class);
//                        startActivity(i);
//
//                        //clear ค่าใน SharedPreferences เมื่อทำการออกจากระบบ
//                        SharedPreferences spDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edDataUser= spDataUser.edit();
//                        edDataUser.clear();
//                        edDataUser.commit();
//
//
//                        SharedPreferences Categorysettings = getSharedPreferences("MACADDREES", 0);
//                        SharedPreferences.Editor prefsEdCategory = Categorysettings.edit();
//                        prefsEdCategory.clear();
//                        prefsEdCategory.commit();
//
//                        SharedPreferences LOGINUSER = getSharedPreferences("LOGINUSER", 0);
//                        SharedPreferences.Editor loginuserss = LOGINUSER.edit();
//                        loginuserss.clear();
//                        loginuserss.commit();
//
//
//                    }
//                });
//                builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.show();
//                break;
////
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void actionBar() {
//      ActionBar actionBar = getSupportActionBar();
////        actionBar.setIcon(R.drawable.iconsmall68x70);
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//
//        }
//    }




}
