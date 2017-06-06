package com.example.wheretomeet;

import android.content.Intent;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 선희 on 2017-06-01.
 */

public class PlaceResponseHandler extends AsyncHttpResponseHandler{

    private String response;
    private FriendsActivity activity;
    private ArrayList<Friend> friends;

    public PlaceResponseHandler(FriendsActivity activity, ArrayList<Friend> friends) {
        this.activity=activity;
        this.friends=friends;
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        if(statusCode==204) {
            Toast.makeText(activity, "There is no right place", Toast.LENGTH_SHORT).show();
        }
        else {
            response = new String(responseBody);
            System.out.println(response);

            Intent map=new Intent(activity.getApplicationContext(),MapsActivity.class);
            map.putExtra("place",response);

            double []locationx=new double[30];
            double []locationy=new double[30];
            ArrayList<String> name=new ArrayList<String>();

            for(int i=0;i<friends.size();i++) {
                locationx[i]=friends.get(i).locationx;
                locationy[i]=friends.get(i).locationy;
                name.add(friends.get(i).name);
            }

            map.putExtra("locationx",locationx);
            map.putExtra("locationy",locationy);
            map.putStringArrayListExtra("name",name);
            map.putExtra("mylat",activity.lat);
            map.putExtra("mylog",activity.log);

            activity.startActivity(map);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {;
        Toast.makeText(activity, "Place get failed", Toast.LENGTH_SHORT).show();
    }
}
