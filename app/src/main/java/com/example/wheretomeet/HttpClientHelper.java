package com.example.wheretomeet;

import android.util.Log;
import com.loopj.android.http.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 선희 on 2017-05-19.
 */

public class HttpClientHelper {

    private static final String URL_API = "http://wheretomeet-api.azurewebsites.net/";
    private static final String NAME="username";
    private static final String PASSWORD="password";
    private static final String EMAIL="email";

    private AsyncHttpClient client;

    public HttpClientHelper(AsyncHttpClient client) {
        this.client=client;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    public void postLogin(String id, String pw, LoginActivity activity) {
        RequestParams params = new RequestParams();
        LoginResponseHandler handler= new LoginResponseHandler(client,activity);
        params.put(NAME,id);
        params.put(PASSWORD,pw);
        String URL = URL_API + "api/login";
        client.post(URL, params, handler);
    }

    public void postRegister(String name, String email, String pw, RegisterActivity activity) {
        RequestParams params = new RequestParams();
        RegisterResponseHandler handler= new RegisterResponseHandler(activity);
        params.put(NAME,name);
        params.put(PASSWORD,pw);
        params.put(EMAIL,email);
        String URL = URL_API + "api/register";
        client.post(URL, params, handler);
    }

    public void postLocation(double x,double y) {
        RequestParams params = new RequestParams();
        String URL=URL_API+"api/location";
        params.put("latitude",x);
        params.put("longitude",y);
        client.put(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("location post success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("location post fail");
            }
        });
    }

    void getFriendsList(LoginActivity activity,String appToken)  {
        String URL=URL_API+"api/friends";
        FriendsListResponseHandler handler= new FriendsListResponseHandler(activity,appToken);
        client.get(URL, handler);
    }

    void getPerfectPlace(ArrayList<String> types,ArrayList<Friend> gofriends,FriendsActivity activity) {
        RequestParams params = new RequestParams();
        PlaceResponseHandler handler= new PlaceResponseHandler(activity,gofriends);
        params.put("types",types);
        ArrayList participants=new ArrayList();
        for(int i=0;i<gofriends.size();i++) {
            participants.add(gofriends.get(i).id);
        }
        params.put("participants",participants);
        String URL=URL_API+"api/perfectplace";
        client.get(URL, params,handler);
    }
}
