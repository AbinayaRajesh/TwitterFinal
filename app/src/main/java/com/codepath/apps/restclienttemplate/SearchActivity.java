package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class SearchActivity extends AppCompatActivity {

    TwitterClient client;
    String query;
    public ArrayList<Tweet> tweets = new ArrayList<>();
    TweetAdapter adapter = new TweetAdapter(tweets);
    RecyclerView rvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = getIntent().getStringExtra("query");

        client = TwitterApp.getRestClient();

        rvTweets = (RecyclerView) findViewById(R.id.search);
        //RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(adapter);


        client.search(query, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray statuses;
                if (response != null) {
                    // Get the docs json array
                    try {
                        statuses = response.getJSONArray("statuses");

                        // Load model objects into the adapter
                        for (int i = 0; i < statuses.length(); i++) {
                            tweets.add(Tweet.fromJSON((statuses.getJSONObject(i)))); // add book through the adapter
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


        });
    }
}
