package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.ComposeDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by arajesh on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {



    TwitterClient client = TwitterApp.getRestClient();
    // Context context;
    FragmentManager fm;


    public void setFm (FragmentManager fm) {
        this.fm = fm;
    }





    public List<Tweet> mTweets;
    Context context;
    ViewGroup mParent;
    private TweetAdapterListener mListener;
    TweetsPagerAdapter adapter;




    // define an interface required by the ViewHolder
    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
       mTweets.clear();
        notifyDataSetChanged();
    }



    // pass in the Tweets array in the constructor
    public TweetAdapter(FragmentManager fm, List<Tweet> tweets, TweetAdapterListener listener) {

        setFm(fm);

        mTweets = tweets;
        mListener = listener;
    }

    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {


        mTweets = tweets;

    }

    // for each row, inflate the layout and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        mParent = parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        // fm = ((Activity) context)).getFragmentManager();;

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);


        return viewHolder;
    }


    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // get the data according to position
        Tweet tweet = mTweets.get(position);
        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(tweet.timestamp);
        // holder.rc = tweet.retweet_count;
        holder.tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
        // holder.fc = tweet.favorites_count;
        holder.tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));


        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

        if(tweet.favorited==true) {
            holder.ivFavorite.setImageResource(R.drawable.ic_favorite);
            holder.tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_like));
        }
        else{
            holder.ivFavorite.setImageResource(R.drawable.ic_unfavorite);
            holder.tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
        }

        if(tweet.reTweeted==true) {
            holder.ivRetweet.setImageResource(R.drawable.ic_vector_retweet);
            holder.tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_retweet));
        }
        else{
            holder.ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
            holder.tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
        }

        if(tweet.url=="") {
            holder.ivImg .setVisibility(View.GONE);
        }
        else{
            holder.ivImg .setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.url)
                    .into(holder.ivImg);

        }
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }







    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.ivFavorite) ImageView ivFavorite;
        @BindView(R.id.ivReply) ImageView ivReply;
        @BindView(R.id.ivRetweet) ImageView ivRetweet;
        @BindView(R.id.ivImg) ImageView ivImg;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
        @BindView(R.id.tvReweetCount) TextView tvRetweetCount;
        @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;
        @BindView(R.id.tvUserName) TextView tvUsername;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivFavorite.setOnClickListener(this);
            ivRetweet.setOnClickListener(this);
            ivReply.setOnClickListener(this);
            tvBody.setOnClickListener(this);
            ivProfileImage.setOnClickListener(this);

        }




//            // handle row click event
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//
//                    if (mListener != null) {
//
//                        // get the position of the row element
//                        int position = getAdapterPosition();
//                        // fire the listener callback
//                        mListener.onItemSelected(view, position);
//                    }



        // outside viewholder with bind

        @Override
        public void onClick(View v) {
            Log.d("onClick", "ddddd");
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);

                if (v.getId() == R.id.ivRetweet) {

                                // get the movie at the position, this won't work if the class is static
                                if (!tweet.reTweeted) {
                                    tweet.retweet_count += 1;
                                    tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
                                    client.reTweet(tweet.uid, new JsonHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            ivRetweet.setImageResource(R.drawable.ic_vector_retweet);
                                            tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_retweet));
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
                                            tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
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
                                    tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_like));
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
                                    tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
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


                    ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance("Replying to " + tweet.user.name,
                            tweet.user.screenName, tweet.uid);

                    composeDialogFragment.show(fm, "fragment_compose");

//                                long in_reply_to_status_id = tweet.uid;
//
//                                // create intent for the new activity
//                                Intent intent = new Intent(context, ComposeActivity.class);
//                                // serialize the movie using parceler, use its short name as a key
//                                intent.putExtra("reply", true);
//                                intent.putExtra("username", tweet.user.screenName);
//                                intent.putExtra("tweet_id", tweet.uid);
//                                // show the activity
//                                context.startActivity(intent);
//                                String message = "Hi";
//                                client.reply(message, in_reply_to_status_id, new JsonHttpResponseHandler() {
//
//
//                                    @Override
//                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//
//
//
//                                    }
//
//
//
//                                    @Override
//                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                        Log.d("TwitterClient", responseString);
//                                        throwable.printStackTrace();
//                                    }
//
//                                    @Override
//                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                        Log.d("TwitterClient", errorResponse.toString());
//                                        throwable.printStackTrace();
//                                    }
//
//                                    @Override
//                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                                        Log.d("TwitterClient", errorResponse.toString());
//                                        throwable.printStackTrace();
//                                    }
//                                });




                }
                else if (v.getId() == R.id.tvBody){
//        FragmentManager fm = getSupportFragmentManager();
//        DetailsDialogFragment detailsDialogFragment = DetailsDialogFragment.newInstance("username");
//        detailsDialogFragment.show(fm, "fragment_edit_name");

//                    DetailsDialogFragment detailsDialogFragment = DetailsDialogFragment.newInstance(tweet);
//                    DetailsDialogFragment.fragmentManager = fm;
//                    DetailsDialogFragment.tweetAdapter= (TweetAdapter) ((RecyclerView) itemView.getParent()).getAdapter();
//                    DetailsDialogFragment.position = getAdapterPosition();
//                    detailsDialogFragment.show(fm, "fragment_details");


//
//
                    // create intent for the new activity
                    Intent intent = new Intent(context, DetailsActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    // show the activity
                    ((Activity) context).startActivityForResult(intent, 3);





                }
                else if (v.getId() == R.id.ivProfileImage){

                    log.d("TweetAdapter", "click");

                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("profile",true);
                    i.putExtra("uid", tweet.uid);
                    i.putExtra("screen_name", tweet.user.screenName);
                    context.startActivity(i);

                }



                }

            }

        }



}












