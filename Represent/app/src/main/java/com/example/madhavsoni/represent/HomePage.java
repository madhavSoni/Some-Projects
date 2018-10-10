package com.example.madhavsoni.represent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationListener;
import java.util.Random;

import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class HomePage extends AppCompatActivity {

    private double longitude;
    private double latitude;
    private static String zipCode = "94704";
    public static int type = 3;
    private static ArrayList<Integer> districtNumbers = new ArrayList<Integer>();
    private static String repState = "CA";
    private static String repCity = "Berkeley";

    public static int getType() {
        return type;
    }


    public static ArrayList<Integer> getDistrictNumbers() {
        return districtNumbers;
    }

    public static String getRepState() {
        return repState;
    }

    public static String getZipCode() {
        return zipCode;
    }

    public static String getRepCity() {
        return repCity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    //Enter A Zipcode
    public void sendButtonOnClick(View v) {
        districtNumbers = new ArrayList<Integer>();
        EditText zip = (EditText) findViewById(R.id.zipCode);
        type = 0;
        if (zip.getText() != null) {
            String zipString = zip.getText().toString();
            zipCode = zipString;
            findDistrict();

        }
    }


    //Current Location
    public void currentButtonOnClick(View v) {
        districtNumbers = new ArrayList<Integer>();
        type = 0;
        findDistrict();

    }

    //Random Location
    public void randomButtonOnClick(View v) {
        type = 2;
        districtNumbers = new ArrayList<Integer>();
        ArrayList<String> keysAsArray = new ArrayList<String>(randomStuff.keySet());
        Random r = new Random();
        String state = keysAsArray.get(r.nextInt(keysAsArray.size()));
        Integer dist = randomStuff.get(state);
        Random rand = new Random();
        int  usedDist = rand.nextInt(dist) + 1;
        repState = state;
        System.out.println("USED DIST:" + usedDist);
        districtNumbers.add(usedDist);

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }
    private LocationManager locationManager;
    private android.location.LocationListener myLocationListener;






    public void findLocation( ){

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } catch (SecurityException e) {
            System.out.println("error"); // lets the user know there is a problem with the gps
        }

    }


    public void findDistrict() {

        String requestURL = "";
        if (type == 0) {
            requestURL = "http://api.geocod.io/v1.3/geocode?postal_code=" + zipCode + "&fields=cd&api_key=656fc9f82b86bb6bfffc078555052cbff2d0b50";
        } else if (type == 1) {
            requestURL = "http://api.geocod.io/v1.3/reverse?q=" + latitude + "," + longitude + "&fields=cd&api_key=656fc9f82b86bb6bfffc078555052cbff2d0b50";
        } else if (type == 2) {
            requestURL = "http://api.geocod.io/v1.3/geocode?postal_code=" + zipCode + "&fields=cd&api_key=656fc9f82b86bb6bfffc078555052cbff2d0b50";
        }

        System.out.println(requestURL);
        RequestQueue mQueue = Volley.newRequestQueue(this);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");


                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                JSONObject field = result.getJSONObject("fields");

                                JSONObject addressComponents = result.getJSONObject("address_components");
                                zipCode = addressComponents.getString("zip");
                                repState = addressComponents.getString("state");
                                repCity = addressComponents.getString("city");
                                System.out.println("ZipCode: " + zipCode);
                                System.out.println("State: " + repState);


                                JSONArray congDistrict = field.getJSONArray("congressional_districts");

                                HashSet<Integer> temp = new HashSet<>();

                                for (int j = 0; j < congDistrict.length(); j++) {
                                    JSONObject district = congDistrict.getJSONObject(j);
                                    int intDist = district.getInt("district_number");
                                    System.out.println("District:" + intDist);
                                    temp.add(intDist);
                                }
                                districtNumbers = new ArrayList<>();
                                districtNumbers.addAll(temp);
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) ;





        mQueue.add(request);
    }




    private HashMap<String, Integer> randomStuff = new HashMap<String, Integer>(){
        {
            put("AL", 7);
            put("AZ", 9);
            put("AR", 4);
            put("CA", 53);
            put("CO", 7);
            put("CT", 5);
            put("FL", 27);
            put("GA", 14);
            put("HI", 2);
            put("ID", 2);
            put("IL", 18);
            put("IN", 9);
            put("IA", 4);
            put("KS", 4);
            put("KY", 6);
            put("LA", 6);
            put("ME", 2);
            put("MD", 8);
            put("MA", 9);
            put("MI", 14);
            put("MN", 4);
            put("MS", 8);
            put("MO", 16);
            put("NE", 3);
            put("NV", 4);
            put("NH", 2);
            put("NJ", 12);
            put("NM", 3);
            put("NY", 27);
            put("NC", 13);
            put("OH", 16);
            put("OK", 5);
            put("OR", 5);
            put("PA", 18);
            put("RI", 2);
            put("SC", 7);
            put("TN", 9);
            put("TX", 36);
            put("UT", 4);
            put("VA", 11);
            put("WA", 3);
            put("WV", 8);
        }

    };



}
