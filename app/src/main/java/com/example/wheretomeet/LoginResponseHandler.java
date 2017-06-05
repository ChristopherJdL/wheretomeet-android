package com.example.wheretomeet;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 선희 on 2017-05-20.
 */

public class LoginResponseHandler extends AsyncHttpResponseHandler {

    private String response;
    private LoginActivity activity;
    private AsyncHttpClient client;
    private String appToken;

    public LoginResponseHandler(AsyncHttpClient client, LoginActivity activity){
        this.client=client;
        this.activity=activity;
    };

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        response = new String(responseBody);
        this.AddtoHeader(response);
        HttpClientHelper helper=new HttpClientHelper(client);
        helper.postLocation(activity.locationy, activity.locationx);
        activity.checkDangerousPermission();
        activity.lm.removeUpdates(activity.mLocationListener);

        helper.getFriendsList(activity,appToken);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        response = new String(responseBody);
        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
    }

    private void AddtoHeader(String response) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appToken = jsonObj.get("appToken").toString();
        client.addHeader("Authorization", "Bearer " + appToken);
    }

}
