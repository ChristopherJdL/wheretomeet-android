package com.example.wheretomeet;

import android.app.Dialog;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 선희 on 2017-06-07.
 */

public class postFriendResponseHandler extends AsyncHttpResponseHandler {

    private FriendsActivity activity;
    private String response;
    private Dialog dialog;

    public postFriendResponseHandler(FriendsActivity activity,Dialog dialog) {
        this.activity=activity;
        this.dialog=dialog;
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        response = new String(responseBody);

        dialog.dismiss();
        activity.friends.clear();
        activity.StoreFriends(response);

        activity.adapter.notifyDataSetChanged();     //refresh


    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        response = new String(responseBody);
        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
    }
}
