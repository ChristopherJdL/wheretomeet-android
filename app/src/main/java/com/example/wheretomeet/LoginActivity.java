package com.example.wheretomeet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.loopj.android.http.AsyncHttpClient;


public class LoginActivity extends AppCompatActivity {

    double locationx;
    double locationy;

    LocationManager lm;

    final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            locationy=longitude;
            locationx=latitude;
        }

        public void onProviderDisabled(String provider) {
            // Disabled
        }

        public void onProviderEnabled(String provider) {
            // Enabled
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // changed
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkDangerousPermission();

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100, 1, mLocationListener);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {   //여기도 권한요청부분
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + "GPS Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + "GPS Permission not Granted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void checkDangerousPermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            System.out.println("GPS Permission OK");
        } else {
            Toast.makeText(this, "GPS No Permission", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "GPS Need Permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
}