package com.example.madhavsoni.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 1/2/2018.
 */

public class More extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private String id = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_name")){

            String imageName = getIntent().getStringExtra("image_name");

            TextView name = findViewById(R.id.moreInfoPanelSwag);
            name.setText(imageName);
        }

        if(getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
            initImageBitmaps();
        }
    }




    public void initImageBitmaps() {

        RequestQueue mQueue = Volley.newRequestQueue(this);

        String requestURL = "https://api.propublica.org/congress/v1/members/"+ id +"/bills/introduced.json";
        System.out.println(requestURL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ArrayList<String> bills = new ArrayList<String>();

                            JSONArray results = response.getJSONArray("results");

                            JSONObject result = results.getJSONObject(0);
                            JSONArray bills2 = result.getJSONArray("bills");
                            System.out.println("EHLLLOO");
                            for (int j = 0; j < 3; j ++) {
                                JSONObject singleBill = bills2.getJSONObject(j);
                                String title = singleBill.getString("short_title");
                                bills.add(title);
                            }

                            TextView name = findViewById(R.id.bill1);
                            name.setText("1. "+bills.get(0));

                            TextView name2 = findViewById(R.id.bill2);
                            name2.setText("2. "+ bills.get(1));

                            TextView name3 = findViewById(R.id.bill3);
                            name3.setText("3. "+ bills.get(2));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @NonNull
            @Override
            public HashMap getHeaders () {
                HashMap headers = new HashMap();
                headers.put("X-API-Key", "HcZW2Ydmb6JDG8E24g9IiwHcizayzu9Z9ove6F7y");
                return headers;
            }
        };

        mQueue.add(request);

    }

    public void sendHome2(View v) {
        Intent intent = new Intent( getBaseContext(), HomePage.class);
        startActivity(intent);
    }

}
