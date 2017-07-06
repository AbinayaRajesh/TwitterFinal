package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    ArrayList<String> followers = new ArrayList<String>();
    ArrayList<String> following = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String screenName = getIntent().getStringExtra("screen_name");
        long uid = getIntent().getIntExtra("uid", 0);
        boolean profile = getIntent().getBooleanExtra("profile", false);

        // create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        // display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        // commit
        ft.commit();
        client = TwitterApp.getRestClient();

        if (profile == false) {


            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // deserialize the User object

                    try {
                        User user = User.fromJSON(response);

                        // set the title of the ActionBar based on the user information
                        getSupportActionBar().setTitle(user.screenName);
                        // populate the user headline
                        populateUserHeadline(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            client.followersMe( new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        User user;
                        for (int i = 0; i < users.length(); i++){
                            user =  User.fromJSON(users.getJSONObject(i));
                            followers.add(user.screenName);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    Spinner s = (Spinner) findViewById(R.id.followers_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, followers);
                    s.setAdapter(adapter);
                }


            });


            client.followingMe( new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        User user;
                        for (int i = 0; i < users.length(); i++){
                            user =  User.fromJSON(users.getJSONObject(i));
                            following.add(user.screenName);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    Spinner s = (Spinner) findViewById(R.id.following_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, following);
                    s.setAdapter(adapter);
                }


            });


        }
        else {
            client.getProfileInfo(screenName, uid, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        User user = User.fromJSON(response);
                        // set the title of the ActionBar based on the user information
                        getSupportActionBar().setTitle(screenName);
                        // populate the user headline
                        populateUserHeadline(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            });

            client.followers(screenName, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        User user;
                        for (int i = 0; i < users.length(); i++){
                            user =  User.fromJSON(users.getJSONObject(i));
                            followers.add(user.screenName);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    Spinner s = (Spinner) findViewById(R.id.followers_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, followers);
                    s.setAdapter(adapter);
                }


            });


            client.following(screenName, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray users = response.getJSONArray("users");
                        User user;
                        for (int i = 0; i < users.length(); i++){
                            user =  User.fromJSON(users.getJSONObject(i));
                            following.add(user.screenName);
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    Spinner s = (Spinner) findViewById(R.id.following_spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_spinner_item, following);
                    s.setAdapter(adapter);
                }


            });
        }




    }

    public void populateUserHeadline(User user){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagLine.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");

        // load profile image with Glide
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);


    }
}
