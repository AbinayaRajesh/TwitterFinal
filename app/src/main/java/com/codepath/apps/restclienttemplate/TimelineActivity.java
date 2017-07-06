package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.ComposeDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.DetailsDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.fragments.DetailsDialogFragment.tweetAdapter;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogListener, DetailsDialogFragment.DetailsDialogListener {
    // implements TweetsListFragment.TweetSelectedListener




    //TweetsListFragment fragmentTweetsList;
    private SwipeRefreshLayout swipeContainer;
    TwitterClient client = TwitterApp.getRestClient();

    private JSONArray tweets;
    TweetsPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // set the adapter for the pager
        adapter = new TweetsPagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(adapter);

        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        //fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        ButterKnife.bind(this);

        tweets = new JSONArray();

        // ComposeDialogFragment.DismissListner(closeListener);






        // Lookup the swipe container view

        // swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                fetchTimelineAsync();
//            }
//        });
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);


        // Find the toolbar view inside the activity layout
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        // setSupportActionBar(toolbar);


        TweetsListFragment fragmentTweetsList;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        MenuItem searchItem = menu.findItem(R.id.miSearch);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform query here
//                client.search(query, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
////                        JSONArray statuses;
////                        if(response != null) {
////                            // Get the docs json array
////                            try {
////                                statuses = response.getJSONArray("statuses");
////                                // Remove all books from the adapter
////                                tweetAdapter.clear();
////                                // Load model objects into the adapter
////                                for (int i = 0; i<statuses.length(); i++) {
////                                    tweets.add(statuses.getJSONObject(i)); // add book through the adapter
////                                }
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////
////
////                            tweetAdapter.notifyDataSetChanged();
////                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                    }
//                });
//                        }
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        JSONArray statuses;
//                        if(response != null) {
//                            // Get the docs json array
//                            try {
//                                statuses = response.getJSONArray("statuses");
//                                // Remove all books from the adapter
//                                tweetAdapter.clear();
//                                // Load model objects into the adapter
//                                for (int i = 0; i<statuses.length(); i++) {
//                                    abooks.add(statuses.getJSONObject(i)); // add book through the adapter
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            tweetAdapter.notifyDataSetChanged();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                    }

//
//                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
//                // see https://code.google.com/p/android/issues/detail?id=24599
////                searchView.clearFocus();
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return true;
    }

    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("username");
        composeDialogFragment.show(fm, "fragment_compose");

    }





    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("profile",false);
        startActivity(i);
    }

    public void onCompose(MenuItem item) {

        showComposeDialog();
//        Intent i = new Intent(this, TimelineActivity.class);
//        startActivity(i);
        // ((HomeTimelineFragment) adapter.getItem(vpPager.getCurrentItem())).addTweet(Tweet);

    }

    public void onSearch(MenuItem item) {
        showComposeDialog();
    }

    @Override
    public void onFinishedTweet(Tweet tweet) {

        // Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();
        adapter.getTimelineInstance().addTweet(tweet);
    }

    public void fetchTimelineAsync() {
        //long Id = tweets.get(tweets.size()-1).uid;
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        // showProgressBar();
        TwitterClient client = TwitterApp.getRestClient();

        client.getHomeTimelineFirst( new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // hideProgressBar();
                // addItems(response);
                tweetAdapter.clear();

                for (int i = 0; i < json.length(); i++) {
                    Tweet tweet = null;
                    try {
                        tweet = Tweet.fromJSON(json.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // tweets.add(tweet);
                    // tweetAdapter.notifyItemInserted(tweets.size() - 1);
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


    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
//    public void loadNextDataFromApi(int offset) {
//        long Id = tweets.get(tweets.size()-1).uid;
//        client.getHomeTimeline(Id, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                hideProgressBar();
//
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            public void onFailure(Throwable e) {
//                hideProgressBar();
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK ) {
            // && requestCode == REQUEST_CODE

            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("NewTweet"));
//            HomeTimelineFragment fragment = (HomeTimelineFragment) adapter.getItem(0);
//            fragment.addTweet(tweet);
        }
        if (requestCode == 3){
            fetchTimelineAsync();
        }
        // ((HomeTimelineFragment) adapter.getItem(vPgr.getCurrentItem())).addTweet(Tweet);
    }



}
