package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import static com.codepath.apps.restclienttemplate.TweetAdapter.getRelativeTimeAgo;

/**
 * Created by arajesh on 6/26/17.
 */

@Parcel
public class Tweet {

    // list out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public User user;
    public String createdAt;
    public String timestamp;
    public long in_reply_to_status_id;
    public int charLeft;
    public boolean favorited;
    public boolean reTweeted;
    public long favorites_count;
    public long retweet_count;
    // public JSONObject entities;
    public String url;

    public Tweet() {}

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.timestamp = getRelativeTimeAgo(tweet.createdAt);
        tweet.charLeft = 140-tweet.body.length();
        tweet.reTweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorites_count = jsonObject.getInt("favorite_count");
        JSONObject entities = jsonObject.getJSONObject("entities");
        tweet.url="";
//        if (entities.getJSONArray("media")!=null && entities.getJSONArray("media").length()>0) {
        if (entities.has("media")){
            tweet.url = entities.getJSONArray("media").getJSONObject(0).getString("media_url");
        }


        if(jsonObject.isNull("in_reply_to_status_id")) {
            tweet.in_reply_to_status_id=0;
        }
        else {
            tweet.in_reply_to_status_id = jsonObject.getInt("in_reply_to_status_id");
        }
        return tweet;

    }




}
