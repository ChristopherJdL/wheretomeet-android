package com.example.wheretomeet;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Friend> friends = new ArrayList<Friend>();
    private ListView listview;
    FriendsAdapter adapter;
    private String appToken;
    ImageButtonOnClickListener movieclick;
    ImageButtonOnClickListener cafeclick;
    ImageButtonOnClickListener restaurantclick;
    ImageButtonOnClickListener subwayclick;
    ImageButtonOnClickListener shoppingclick;
    double lat;
    double log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);

        appToken = getIntent().getStringExtra("appToken");

        this.StoreFriends(getIntent().getStringExtra("friends"));

        lat=getIntent().getExtras().getDouble("lat");
        log=getIntent().getExtras().getDouble("log");

        adapter = new FriendsAdapter(this, R.layout.list_item, friends);

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        final ImageButton movie = (ImageButton) this.findViewById(R.id.movie);
        final ImageButton cafe = (ImageButton) this.findViewById(R.id.cafe);
        final ImageButton restaurant = (ImageButton) this.findViewById(R.id.restaurant);
        final ImageButton subway = (ImageButton) this.findViewById(R.id.subway);
        final ImageButton shopping = (ImageButton) this.findViewById(R.id.shopping);

        movieclick=new ImageButtonOnClickListener(movie, R.drawable.movie_c, R.drawable.movie);
        cafeclick=new ImageButtonOnClickListener(cafe, R.drawable.cafe_c, R.drawable.cafe);
        restaurantclick=new ImageButtonOnClickListener(restaurant,R.drawable.restaurant_c, R.drawable.restaurant);
        subwayclick=new ImageButtonOnClickListener (subway,R.drawable.subway_c, R.drawable.subway);
        shoppingclick=new ImageButtonOnClickListener (shopping,R.drawable.shopping_c, R.drawable.shopping);

        movie.setOnClickListener(movieclick);
        cafe.setOnClickListener(cafeclick);
        restaurant.setOnClickListener(restaurantclick);
        subway.setOnClickListener(subwayclick);
        shopping.setOnClickListener(shoppingclick);
    }

    public void goMap(View v) {

        ArrayList <Friend>participants = new ArrayList<Friend>();
        ArrayList<String> types = new ArrayList<String>();

        if(cafeclick.check) {
            types.add("cafe");
        }
        if(movieclick.check) {
            types.add("movie_theater");
        }
        if(subwayclick.check) {
            types.add("subway_station");
        }
        if(shoppingclick.check) {
            types.add("shopping_mall");
        }
        if(restaurantclick.check) {
            types.add("restaurant");
        }

        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).check) {
                participants.add(friends.get(i));
            }
        }
        if(types.size()==0||participants.size()==0) {
            if (types.size() == 0) {
                Toast.makeText(this, "Please select the place types", Toast.LENGTH_SHORT).show();
            }
            if (participants.size() == 0) {
                Toast.makeText(this, "Please select the friends", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Authorization", "Bearer " + appToken);

            HttpClientHelper helper = new HttpClientHelper(client);
            helper.getPerfectPlace(types, participants, this);
        }
    }

    public void StoreFriends(String value) {
        JSONArray jsonArr;
        JSONObject friend;
        try {
            jsonArr = new JSONArray(value);
            for (int i = 0; i < jsonArr.length(); i++) {
                friend = jsonArr.getJSONObject(i);

                Friend f = new Friend();
                f.id = friend.getInt("id");
                f.name = friend.getString("username");
                f.email = friend.getString("email");
                f.locationx = friend.getDouble("lastKnownX");
                f.locationy = friend.getDouble("lastKnownY");

                this.friends.add(f);
            }
        } catch (JSONException e) {
            System.out.println("Parsing Failed");
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setTitle("Add Friend");
        final EditText username = (EditText) dialog.findViewById(R.id.username);
        final Button add=(Button) dialog.findViewById(R.id.Add);

        final FriendsActivity activity=this;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Authorization", "Bearer " + appToken);
                HttpClientHelper helper = new HttpClientHelper(client);
                helper.postFriend(username.getText().toString(), activity,dialog);
            }
        });

        final ImageButton close=(ImageButton) dialog.findViewById(R.id.Close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });
        dialog.show();
    }

}
