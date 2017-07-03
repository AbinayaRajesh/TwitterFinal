package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arajesh on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {





    private List<Tweet> mTweets;
    Context context;

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
       mTweets.clear();
        notifyDataSetChanged();
    }

    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    // for each row, inflate the layout and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

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

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.ivFavorite) ImageView ivFavorite;
        @BindView(R.id.ivReply) ImageView ivReply;
        @BindView(R.id.reTweet) ImageView ivRetweet;
        @BindView(R.id.ivImg) ImageView ivImg;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
        @BindView(R.id.tvReweetCount) TextView tvRetweetCount;
        @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;
        @BindView(R.id.tvUserName) TextView tvUsername;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//
//            ivRetweet.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    TwitterClient client = TwitterApp.getRestClient();
//                    // gets item position
//                    int position = getAdapterPosition();
//                    final Tweet tweet = mTweets.get(position);
//                    // make sure the position is valid, i.e. actually exists in the view
//                    if (position != RecyclerView.NO_POSITION) {
//                        // get the movie at the position, this won't work if the class is static
//                        if (!tweet.reTweeted) {
//                            client.reTweet(tweet.uid, new JsonHttpResponseHandler() {
//                                @Override
//                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                    ivRetweet.setImageResource(R.drawable.ic_vector_retweet);
//                                    tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_retweet));
//                                    tweet.retweet_count+=1;
//                                    tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
//                                }
//
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                    Log.i("TweetAdapter", "here");
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                    super.onFailure(statusCode, headers, responseString, throwable);
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//                            });
//                        }
//                        else {
//                            client.unRetweet(tweet.uid, new JsonHttpResponseHandler() {
//                                @Override
//                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                    ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
//                                    tvRetweetCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
//                                    tweet.retweet_count-=1;
//                                    tvRetweetCount.setText(String.valueOf(tweet.retweet_count));
//                                }
//
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                    Log.i("TweetAdapter", "here");
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                    super.onFailure(statusCode, headers, responseString, throwable);
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//                        });
//
//
//                    }
//                    tweet.reTweeted = !tweet.reTweeted;
//                    }
//                }
//
//            });
//
//
//            tvBody.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("TimelineActivity", "HERE");
//                    TwitterClient client = TwitterApp.getRestClient();
//                    // gets item position
//                    int position = getAdapterPosition();
//                    Tweet tweet = mTweets.get(position);
//                    // create intent for the new activity
//                    Intent intent = new Intent(context, DetailsActivity.class);
//                    // serialize the movie using parceler, use its short name as a key
//                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                    // show the activity
//                    ((Activity) context).startActivityForResult(intent, 3);
//                }
//            });
//
//
//
//            ivFavorite.setOnClickListener(new View.OnClickListener() {
//                // launched for a result
//                public void onClick(View v) {
//
//                    TwitterClient client = TwitterApp.getRestClient();
//
//                    // gets item position
//                    int position = getAdapterPosition();
//                    final Tweet tweet = mTweets.get(position);
//
//                    // make sure the position is valid, i.e. actually exists in the view
//                    if (position != RecyclerView.NO_POSITION) {
//                        // get the movie at the position, this won't work if the class is static
//
//
//                        if (tweet.favorited == false) {
//
//                            client.addFavorite(tweet.uid, new JsonHttpResponseHandler() {
//                                @Override
//                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                    ivFavorite.setImageResource(R.drawable.ic_favorite);
//                                    tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.inline_action_like));
//                                    tweet.favorites_count+=1;
//                                    tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//                            });
//                        } else {
//
//                            client.unFavorite(tweet.uid, new JsonHttpResponseHandler() {
//                                @Override
//                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                    ivFavorite.setImageResource(R.drawable.ic_unfavorite);
//                                    tvFavoriteCount.setTextColor(ContextCompat.getColor(context, R.color.medium_gray));
//                                    tweet.favorites_count-=1;
//                                    tvFavoriteCount.setText(String.valueOf(tweet.favorites_count));
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//
//                                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                                }
//                            });
//                        }
//                    }
//                    tweet.favorited = !tweet.favorited;
//                }
//
//            });
//
//            ivReply.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    TwitterClient client = TwitterApp.getRestClient();
//
//                    // gets item position
//                    int position = getAdapterPosition();
//                    // make sure the position is valid, i.e. actually exists in the view
//                    if (position != RecyclerView.NO_POSITION) {
//                        // get the movie at the position, this won't work if the class is static
//                        Tweet tweet = mTweets.get(position);
//
//                        long in_reply_to_status_id = tweet.uid;
//
//                        // create intent for the new activity
//                        Intent intent = new Intent(context, ComposeActivity.class);
//                        // serialize the movie using parceler, use its short name as a key
//                        intent.putExtra("reply", true);
//                        intent.putExtra("username", tweet.user.screenName);
//                        intent.putExtra("tweet_id", tweet.uid);
//                        // show the activity
//                        context.startActivity(intent);
//                        String message = "Hi";
//
//
//
//
//                        client.reply(message, in_reply_to_status_id, new JsonHttpResponseHandler() {
//
//
//                            @Override
//                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                                Log.i("TweetAdapter","heree");
//                                ivRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
//                                }
//
//
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                Log.d("TwitterClient", responseString);
//                                throwable.printStackTrace();
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                                Log.d("TwitterClient", errorResponse.toString());
//                                throwable.printStackTrace();
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                                Log.d("TwitterClient", errorResponse.toString());
//                                throwable.printStackTrace();
//                            }
//                        });
//                    }
//                }
//            });
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




}
