package com.example.wheretomeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void Register(View v){

        HttpClientHelper helper=new HttpClientHelper(new AsyncHttpClient());

        final EditText name=(EditText)findViewById(R.id.name);
        final EditText email=(EditText)findViewById(R.id.email);
        final EditText pw=(EditText)findViewById(R.id.pw);
        final EditText pwc=(EditText)findViewById(R.id.pwc);

        if(pw.getText().toString().equals(pwc.getText().toString())) {
            helper.postRegister(name.getText().toString(), email.getText().toString(), pw.getText().toString(),this);
        }
        else {
            Toast.makeText(this, "Passwords are different", Toast.LENGTH_SHORT).show();
        }
    }
}
