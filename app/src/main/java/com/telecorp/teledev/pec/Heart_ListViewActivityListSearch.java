package com.telecorp.teledev.pec;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Heart_ListViewActivityListSearch extends ToolbarActivity   {

    private int  userId;
    private String username;
    private AsyncHttpClient mClient = new AsyncHttpClient();

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


    private TestAdapter adapter;

    private  ListView listView;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_listchannelsearch2);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main_listchannelsearch2, contentFrameLayout);
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


        TextView  settitle = (TextView) findViewById(R.id.settitle);
        settitle.setText("สรุป การเต้นของหัวใจ");
        listView = (ListView) findViewById(R.id.listView1);



        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isOnline()){
                    swipeRefreshLayout.setRefreshing(true);
                    Load_data(username);
                }else{
                    Toast.makeText(getApplication(), "No Internet", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        displayListView();
    }

    private void displayListView() {

        adapter = new TestAdapter(Heart_ListViewActivityListSearch.this );// your adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text


            }
        });
//

        swipeRefreshLayout.setRefreshing(false);
    }

    private void SetDataList () {

        displayListView(  );
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    public void onBackPressed() {
        finish();


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

    private int identifier = 0;


    @Override
    protected void onPause() {
        super.onPause();


    }



    @Override
    protected void onResume() {
        super.onResume();



    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    boolean check_notify=false;


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences spGetDataUser = getSharedPreferences("DATA_USER", Context.MODE_PRIVATE);
        userId = spGetDataUser.getInt("USER_ID", 0);
        username = spGetDataUser.getString("USERNAME", "");

        Load_data(username);
    }



    private void Load_data(String username) {
//        String urlD=  getString(R.string.url)+"/APITravel/Cat1" ;
        String urlD = "http://boxdoo.com/apiwd/Wristband/index.php/APITravel/Cat1?username=" + username;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlD, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Debug.out("onSuccess");
                String str = null;
                try {
                    str = new String(response, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                pd_Dialog.dismiss();
                Gson gson = new Gson();
                M_Heart list_data_heart=   gson.fromJson(str.toString(), M_Heart.class);
                File_Confix_Data.heart = list_data_heart ;
                SetDataList ( );
//
//                if(File_Confix_Data.list_data_youtube.getPageInfo().getResultsPerPage()>0){
//
//                }else {
//                    PSI_Debug.out("ON data "+str);
//                }
//
////                 PSI_Debug.out("onSuccess "+str);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                String str = null;
                try {
                    str = new String(response, "UTF-8");
                } catch (Exception ess) {
                    // TODO Auto-generated catch block
                    ess.printStackTrace();
                }
                Debug.out("onFailure " + str);
                Toast.makeText(getApplicationContext(),
                        "การดึงข้อมูลเกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
            }
        });
//
    }


    public class TestAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

        public TestAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {

                viewHolder = new ViewHolder();

                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.activity_item_channel, null);

                // holder.code = (TextView) convertView.findViewById(R.id.code);
                viewHolder.name = (TextView) convertView.findViewById(R.id.title);
                viewHolder.continent = (TextView) convertView.findViewById(R.id.artist);
                viewHolder.region = (TextView) convertView.findViewById(R.id.count_view);
                viewHolder.list_image = (ImageView) convertView.findViewById(R.id.list_image);
                viewHolder.set_onclick_bg = (RelativeLayout) convertView.findViewById(R.id.set_onclick_bg);



                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText( File_Confix_Data.heart.getOutput().get(position).getName_user());
            viewHolder.continent.setText( "การเต้นของหัวใจ  " +File_Confix_Data.heart.getOutput().get(position).getMeasurement_user()+"  BMP");//measurement_user
            viewHolder.region.setText("วันที่ "+File_Confix_Data.heart.getOutput().get(position).getDate_user()+" "+
                    File_Confix_Data.heart.getOutput().get(position).getTime_user());


            Glide.with(Heart_ListViewActivityListSearch.this )
                    .load(R.drawable.kisspng)
                    .placeholder(R.drawable.kisspng)
                    .into(viewHolder.list_image);
            return convertView;
        }

        @Override
        public int getCount() {
            int dddd=0;



            try {
                dddd=  File_Confix_Data.heart.getOutput().size() ;

            } catch(Exception e) {
                dddd=0;

            }

            return dddd;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * # CAUTION:
         * ViewHolder must extend from ParallaxViewHolder
         */
        class ViewHolder  {
            TextView code;
            TextView name;
            TextView continent;
            TextView region;
            ImageView list_image;
            RelativeLayout set_onclick_bg;
        }

    }
    byte[] value_write1=null;
    int count_write=0;
    ArrayList<byte[]> alist_l1ssss=new ArrayList<byte[]>();
    boolean check_write=false;







    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}