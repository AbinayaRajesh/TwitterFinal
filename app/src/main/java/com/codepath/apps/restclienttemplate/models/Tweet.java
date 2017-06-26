package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import static com.codepath.apps.restclienttemplate.TweetAdapter.getRelativeTimeAgo;

/**
 * Created by arajesh on 6/26/17.
 */

public class Tweet {

    // list out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public User user;
    public String createdAt;
    public String timestamp;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = tweet.user.fromJSON(jsonObject.getJSONObject("user"));
        tweet.timestamp = getRelativeTimeAgo(tweet.createdAt);
        return tweet;

    }




}
