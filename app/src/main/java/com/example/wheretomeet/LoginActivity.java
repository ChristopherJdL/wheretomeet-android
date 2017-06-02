package com.example.wheretomeet;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LoginClick(View v) {

        HttpClientHelper helper=new HttpClientHelper(new AsyncHttpClient());

        final EditText ID=(EditText)findViewById(R.id.LoginText);
        final EditText PW=(EditText)findViewById(R.id.PWText);

        helper.postLogin(ID.getText().toString(),PW.getText().toString(),this);
    }

    public void RegisterClick(View v) {
        Intent register=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(register);
    }
}