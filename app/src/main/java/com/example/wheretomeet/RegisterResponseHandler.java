package com.example.wheretomeet;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 선희 on 2017-05-20.
 */

public class RegisterResponseHandler extends AsyncHttpResponseHandler{

    private RegisterActivity activity;

    public RegisterResponseHandler(RegisterActivity activity){
        this.activity=activity;
    };

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Toast.makeText(activity, "Registered", Toast.LENGTH_SHORT).show();
        activity.finish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String response=new String(responseBody);
        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
    }
}
