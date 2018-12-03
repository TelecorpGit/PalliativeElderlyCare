package com.telecorp.teledev.pec;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;

public class GridViewExampleActivity extends ToolbarActivity {
    /** Called when the activity is first created. */
	
	private MainMenu_GridviewAdapter mAdapter;
	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;
	
	private GridView gridView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.main, contentFrameLayout);
        setTitle("PEC");

        prepareList_NOT_WIFI();


        // prepared arraylist and passed it to the Adapter class

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        mAdapter = new MainMenu_GridviewAdapter(this,listCountry, listFlag);
        gridView.setAdapter(mAdapter);
        
        // Implement On Item click listener
        gridView.setOnItemClickListener(new OnItemClickListener() 
        {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			    if(position==0){

                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(GridViewExampleActivity.this);
                    //builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle("บันทึกข้อมูลต่อครั้ง/นาที");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GridViewExampleActivity.this, android.R.layout.select_dialog_singlechoice);

                    arrayAdapter.add("0.5");
                    arrayAdapter.add("1");
                    arrayAdapter.add("2");
                    arrayAdapter.add("3");
                    arrayAdapter.add("4");
                    arrayAdapter.add("5");
                    arrayAdapter.add("6");
                    arrayAdapter.add("7");
                    arrayAdapter.add("8");
                    arrayAdapter.add("9");
                    arrayAdapter.add("10");

                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            double value = Double.parseDouble(strName);
                            value=value*1000;
                             File_Confix_Data.delayMillis=(int)value;

                            Intent myIntent = new Intent(GridViewExampleActivity.this, MeasureHeart_Main .class);
                            startActivity(myIntent);


//                            AlertDialog.Builder builderInner = new AlertDialog.Builder(GridViewExampleActivity.this);
//                            builderInner.setMessage(strName);
//                            builderInner.setTitle("Your Selected Item is");
//                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,int which) {
//                                    dialog.dismiss();
//                                    Intent myIntent = new Intent(GridViewExampleActivity.this, MeasureHeart_Main .class);
//                                    startActivity(myIntent);
//                                }
//                            });
//                            builderInner.show();
                        }
                    });
                    builderSingle.show();

                }else  if(position==1){

                        Intent myIntent = new Intent(GridViewExampleActivity.this, Pressure_Main .class);
                        startActivity(myIntent);

                } else  if(position==2){

                    Intent myIntent = new Intent(GridViewExampleActivity.this, Heart_ListViewActivityListSearch.class);
                    startActivity(myIntent);



                } else  if(position==3){

                    Intent myIntent = new Intent(GridViewExampleActivity.this, Pressure_ListViewActivityListSearch.class);
                    startActivity(myIntent);



                }else{

                }

				//Toast.makeText(GridViewExampleActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
			}
		});
        
    }
    

    public void prepareList_NOT_WIFI()
    {
        listCountry = new ArrayList<String>();

        listCountry.add("อัตราการเต้นของหัวใจ");
        listCountry.add("ความดัน");
        listCountry.add("สรุป การเต้นของหัวใจ");
        listCountry.add("สรุป ความดัน");



        listFlag = new ArrayList<Integer>();
        listFlag.add(R.drawable.kisspng);
        listFlag.add(R.drawable.xu);
        listFlag.add(R.drawable.infographic3);
        listFlag.add(R.drawable.bloodpresure);


    }


//    @Override
//    public void onBackPressed() {
//
//
//    }


    @Override
    protected void onStop() {
        super.onStop();


    }


    @Override
    protected void onStart() {
        super.onStart();


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



    boolean check_connext = false;
}