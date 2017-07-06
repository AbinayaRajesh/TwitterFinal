package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by arajesh on 7/3/17.
 */

public class HomeTimelineFragment extends TweetsListFragment {

    // FragmentManager fm;




    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }
// Instance of the progress action-view
    // MenuItem miActionProgressItem;


//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Store instance of the menu item containing progress
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        // Extract the action-view from the menu item
//        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        // Return to finish
//        return super.onPrepareOptionsMenu(menu);
//    }

//    public void showProgressBar() {
//        // Show progress item
//        miActionProgressItem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        // Hide progress item
//        miActionProgressItem.setVisible(false);
//    }
//
//    private final int REQUEST_CODE = 20;
//    private final int RESULT_OK = 20;
//
//    // Store a member variable for the listener
//    private EndlessRecyclerViewScrollListener scrollListener;


//    public void onComposeAction(MenuItem mi) {
//        // showEditDialog();
//
//        Intent i = new Intent(HomeTimelineFragment.this, ComposeActivity.class);
//        i.putExtra("reply", false);
//        i.putExtra("username", "abi"); // pass arbitrary data to launched activity
//        startActivityForResult(i, REQUEST_CODE);
//    }


    // private SwipeRefreshLayout swipeContainer;
    // TextView tvBody = (TextView) findViewById(R.id.tvBody);
    // public SwipeRefreshLayout swipeContainer;
    TwitterClient client;
    TweetsListFragment frag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient();

        populateTimeline();
        TweetsListFragment frag = ((TweetsListFragment)this.getParentFragment());
        this.fm = fm;
        // swipeContainer = frag.swipeContainer;



    }



    public void fetchTimelineAsync() {
        //long Id = tweets.get(tweets.size()-1).uid;
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        // showProgressBar();

        client.getHomeTimelineFirst( new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // hideProgressBar();
                addItems(json);
                tweetAdapter.clear();

                for (int i = 0; i < json.length(); i++) {
                    Tweet tweet = null;
                    try {
                        tweet = Tweet.fromJSON(json.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tweets.add(tweet);
                    tweetAdapter.notifyItemInserted(tweets.size() - 1);
                }

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                // hideProgressBar();
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }



    private void populateTimeline(){



        client.getHomeTimelineFirst( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // iterate through the JSON array
//                // for each entry, deserialize the JSON object
//                for (int i=0; i < response.length(); i++) {
//                    // convert each object to a Tweet model
//                    // add that Tweet model to our data source
//                    // notify the adapter that we've added an item
//                    try {
//                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
//                        tweets.add(tweet);
//                        tweetAdapter.notifyItemInserted(i-1);
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
               addItems(response);


            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });



    }
}
