package com.example.wheretomeet;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String name;
    private double locationx;
    private double locationy;
    private double[] flocationx;
    private double[] flocationy;
    private ArrayList<String> fname;
    private double mylat;
    private double mylog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        storePlace(getIntent().getExtras().getString("place"));
        flocationx=getIntent().getDoubleArrayExtra("locationx");
        flocationy=getIntent().getDoubleArrayExtra("locationy");
        fname=getIntent().getStringArrayListExtra("name");
        mylat=getIntent().getExtras().getDouble("mylat");
        mylog=getIntent().getExtras().getDouble("mylog");

        LatLng place = new LatLng(locationx , locationy);
        mMap.addMarker(new MarkerOptions().position(place).title(name).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.marker))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,14));

        for(int i=0;i<fname.size();i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(flocationy[i], flocationx[i])).title(fname.get(i)));
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(mylat, mylog)).title("ME"));
    }

    private void storePlace (String response) {

        JSONParser parser = new JSONParser();
        JSONObject place = null;

        try {
            place = (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        name=place.get("name").toString();
        locationx=(double)place.get("longitude");
        locationy=(double)place.get("latitude");
    }
}