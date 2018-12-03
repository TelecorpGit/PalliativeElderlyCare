package com.telecorp.teledev.pec;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.telecorp.teledev.pec.adapter.DeviceAdapter_BluetoothFastBle;

public class Toolbart extends AppCompatActivity {

    private Dialog dialog;
    private DeviceAdapter_BluetoothFastBle mDeviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        actionBar();
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Toolbart.this, LoginActivity.class);
//            startActivity(intent);
//            super.onBackPressed();
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    private void actionBar() {
//      ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
////            Intent intent = new Intent(Toolbart.this, LoginActivity.class);
////            startActivity(intent);
//            actionBar.setDisplayShowHomeEnabled(true);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                    Intent intent = new Intent(Toolbart.this, LoginActivity.class);
                    startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
