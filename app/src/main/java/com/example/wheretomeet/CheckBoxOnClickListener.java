package com.example.wheretomeet;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by 선희 on 2017-05-23.
 */

public class CheckBoxOnClickListener implements View.OnClickListener {

    private int position;
    private ArrayList<Friend> friends;

    public CheckBoxOnClickListener(int i, ArrayList<Friend> friends){
        position=i;
        this.friends=friends;
    };
    @Override
    public void onClick(View view) {
        if (friends.get(position).check) {
            friends.get(position).check=false;
        }
        else
            friends.get(position).check=true;
    }
}