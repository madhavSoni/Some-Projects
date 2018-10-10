package com.example.madhavsoni.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import com.android.volley.*;
import com.android.volley.Response;

import org.json.*;
import com.android.volley.toolbox.JsonObjectRequest;


import java.util.ArrayList;

public class TabFragment1 extends Fragment {

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mLastNames = new ArrayList<>();
    private ArrayList<String> mIDs = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mWebsite = new ArrayList<>();
    private ArrayList<String> mParty = new ArrayList<>();
    private RequestQueue mQueue;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.tab_fragment1, container, false);
        RequestQueue temp = Volley.newRequestQueue(view.getContext());
        mQueue = temp;
        initImageBitmaps();

        return view;
    }


    public void initImageBitmaps() {



        String requestURL = "https://api.propublica.org/congress/v1/members/senate/" + HomePage.getRepState() + "/current.json";
        System.out.println(requestURL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                String id = result.getString("id");
                                mIDs.add(id);
                                String name = result.getString("name");
                                System.out.println("Senator Name: " + name);
                                mNames.add(name);
                                String last_name = result.getString("last_name");
                                mLastNames.add(last_name);
                                String party = result.getString("party");
                                mParty.add(party);

                                String web =  "http://www."+ last_name.toLowerCase() +".senate.gov/";
                                mWebsite.add(web);

                                String url = "http://bioguide.congress.gov/bioguide/photo/" + id.substring(0,1) +"/" + id + ".jpg";
                                mImageUrls.add(url);
                                System.out.println(mImageUrls.get(mImageUrls.size() - 1));

                            }

                            initRecyclerView();

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
        System.out.println(mImageUrls);

        mQueue.add(request);

    }






    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mNames, mImageUrls, mWebsite, mParty, mIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}