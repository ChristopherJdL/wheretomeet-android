package com.example.wheretomeet;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 선희 on 2017-05-21.
 */

public class FriendsAdapter extends BaseAdapter {

    private ArrayList<Friend> friends;
    private Context context;
    private int layout;

    public FriendsAdapter(Context context, int layout, ArrayList<Friend> friends) {
        this.friends = friends;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Friend getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);
        }
        final TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(friends.get(i).name);

        final TextView location = (TextView) view.findViewById(R.id.location);
        location.setText(getAddress(friends.get(i).locationy,friends.get(i).locationx));

        final CheckBox box = (CheckBox) view.findViewById(R.id.checkBox);

        box.setOnClickListener(new CheckBoxOnClickListener(i,friends));

        return view;
    }

    private String getAddress(double lat, double lng) {

        String address=null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> list = null;
        try{
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch(Exception e){
            e.printStackTrace();
        }
        if(list == null){
            System.out.println("Fail to get address from location");
            return null;
        }
        if(list.size() > 0){
            Address addr = list.get(0);
            address = removeNULL(addr.getAdminArea())+" "
                    + removeNULL(addr.getLocality()) + " "
                    + removeNULL(addr.getThoroughfare()) + " "
                    + removeNULL(addr.getFeatureName());
        }
        return address;
    }
    private String removeNULL(String string) {
        if (string==null) {
            return "";
        }
        else {
            return string;
        }
    }

}