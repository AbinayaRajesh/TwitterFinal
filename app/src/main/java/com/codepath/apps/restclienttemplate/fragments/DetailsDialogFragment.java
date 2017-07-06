package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
// ...

public class DetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    // Tweet tweet;
    TwitterClient client;
    public static FragmentManager fragmentManager;
    public static int position;
    public static TweetAdapter tweetAdapter;
    Tweet tweet;

    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvUserName) TextView tvUsername;
    @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;
    @BindView(R.id.tvRetweetCount) TextView tvRetweetCount;
    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
    @BindView(R.id.ivImg) ImageView ivImg;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.etReply) EditText etReply;

    @BindView(R.id.ivFavorite) ImageView ivFavorite;
    @BindView(R.id.ivReply) ImageView ivReply;
    @BindView(R.id.ivRetweet) ImageView ivRetweet;


    public DetailsDialogFragment() {}

//    public interface DetailsDialogListener {
//        void onFinishedTweet(Tweet tweet);
//    }

    public interface DetailsDialogListener {
        // void onFinishedTweet(Tweet tweet);
    }


    public static DetailsDialogFragment newInstance(Tweet tweet) {
        DetailsDialogFragment frag = new DetailsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("tweet", Parcels.wrap(tweet));
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        ButterKnife.bind(this, view);
        client = TwitterApp.getRestClient();
        // User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        tweet = Parcels.unwrap(getArguments().getParcelable("tweet"));



        tvBody.setText(tweet.body);
        tvUsername.setText(tweet.user.name);
        tvFavoriteCount.setText(tweet.favorites_count+" FAVORITES");
        tvRetweetCount.setText(tweet.retweet_count+" RETWEETS");
        tvTimeStamp.setText(tweet.timestamp);
        etReply.setHint("Reply to "+ tweet.user.name);

        if(tweet.favorited==true) {

            ivFavorite.setImageResource(R.drawable.ic_favorite);

        }
        else{
            ivFavorite.setImageResource(R.drawable.ic_unfavorite);
        }

        if(tweet.reTweeted==true) {
            ivRetweet.setImageResource(R.drawable.ic_vector_retweet);

        }
        else{
            ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
        }

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("username", "Enter Name");
        getDialog().setTitle(title);



    }




    @Override
    public void onClick(View v) {

        // GET THE TWEET

        if (v.getId() == R.id.ivRetweet) {

            // get the movie at the position, this won't work if the class is static
            if (!tweet.reTweeted) {
                tweet.retweet_count += 1;
                tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
                client.reTweet(tweet.uid, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        ivRetweet.setImageResource(R.drawable.ic_vector_retweet);
                        tvRetweetCount.setTextColor(ContextCompat.getColor(getContext(), R.color.inline_action_retweet));
                        // tweet.retweet_count += 1;
                        // tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.i("TweetAdapter", "here");
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            } else {
                tweet.retweet_count -= 1;
                tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
                client.unRetweet(tweet.uid, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                        tvRetweetCount.setTextColor(ContextCompat.getColor(getContext(), R.color.medium_gray));
                        // tweet.retweet_count -= 1;
                        // tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.i("TweetAdapter", "here");
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


            }
            tweet.reTweeted = !tweet.reTweeted;


        }
        else if (v.getId() == R.id.ivFavorite){


            if (tweet.favorited == false) {
                tweet.favorites_count+=1;
                tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));

                client.addFavorite(tweet.uid, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        ivFavorite.setImageResource(R.drawable.ic_favorite);
                        tvFavoriteCount.setTextColor(ContextCompat.getColor(getContext(), R.color.inline_action_like));
                        // tweet.favorites_count+=1;
                        // tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            } else {
                tweet.favorites_count-=1;
                tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));

                client.unFavorite(tweet.uid, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        ivFavorite.setImageResource(R.drawable.ic_unfavorite);
                        tvFavoriteCount.setTextColor(ContextCompat.getColor(getContext(), R.color.medium_gray));
                        // tweet.favorites_count-=1;
                        // tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }

            tweet.favorited = !tweet.favorited;
        }

        else if (v.getId() == R.id.ivReply){

//            long in_reply_to_status_id = tweet.uid;
//
//            // create intent for the new activity
//            Intent intent = new Intent(context, ComposeActivity.class);
//            // serialize the movie using parceler, use its short name as a key
//            intent.putExtra("reply", true);
//            intent.putExtra("username", tweet.user.screenName);
//            intent.putExtra("tweet_id", tweet.uid);
//            // show the activity
//            context.startActivity(intent);
//            String message = "Hi";
//
//
//
//
//            client.reply(message, in_reply_to_status_id, new JsonHttpResponseHandler() {
//
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//
//
//
//                }
//
//
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    Log.d("TwitterClient", responseString);
//                    throwable.printStackTrace();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    Log.d("TwitterClient", errorResponse.toString());
//                    throwable.printStackTrace();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                    Log.d("TwitterClient", errorResponse.toString());
//                    throwable.printStackTrace();
//                }
//            });




        }




//        if (v.getId() == R.id.tweet_action) {
//            String data = etTweet.getText().toString();
//            if (!reply) {
//                client.sendTweet(data, (new JsonHttpResponseHandler() {
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        Tweet tweet = null;
//
//                        try {
//                            tweet = Tweet.fromJSON(response);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        ComposeDialogListener listener = ((ComposeDialogListener) getActivity());
//                        listener.onFinishedTweet(tweet);
//                        dismiss();
//                        return;
//
//                    }
//                }));
//
//            } else {
//
//
//                client.reply(userId + " " + data, tweet_id, (new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        // Intent intent = new Intent();
//                        Tweet tweet = null;
//
//                        try {
//                            tweet = Tweet.fromJSON(response);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        ComposeDialogListener listener = (ComposeDialogListener) getActivity();
//                        listener.onFinishedTweet(tweet);
//                        dismiss();
//                        return;
//                    }
//                }));
//            }
//        }
//        else if (v.getId() == R.id.cancel_action){
//            dismiss();
//        }


    }
}