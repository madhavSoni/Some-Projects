package com.example.madhavsoni.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TabFragment2 extends Fragment {

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mLastNames = new ArrayList<>();
    private ArrayList<String> mIDs = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mWebsite = new ArrayList<>();
    private ArrayList<String> mParty = new ArrayList<>();
    private RequestQueue mQueue;
    private ArrayList<Integer> district = HomePage.getDistrictNumbers();


    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        RequestQueue temp = Volley.newRequestQueue(view.getContext());
        mQueue = temp;
        initImageBitmaps();

        return view;
    }


    public void initImageBitmaps() {


        System.out.println("Districts: " + district);

        for (int i = 0; i < district.size(); i += 1) {
            String requestURL = "https://api.propublica.org/congress/v1/members/house/" + HomePage.getRepState() + "/" + district.get(i) + "/current.json";
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

                                    String web = "http://www." + last_name.toLowerCase() + ".house.gov/";
                                    mWebsite.add(web);

                                    String url = "http://bioguide.congress.gov/bioguide/photo/" + id.substring(0, 1) + "/" + id + ".jpg";
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
                public HashMap getHeaders() {
                    HashMap headers = new HashMap();
                    headers.put("X-API-Key", "HcZW2Ydmb6JDG8E24g9IiwHcizayzu9Z9ove6F7y");
                    return headers;
                }
            };
            mQueue.add(request);
        }
        System.out.println(mImageUrls);


    }





    private void initRecyclerView(){
        System.out.println(mImageUrls.get(mImageUrls.size() - 1));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mNames, mImageUrls, mWebsite, mParty, mIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}