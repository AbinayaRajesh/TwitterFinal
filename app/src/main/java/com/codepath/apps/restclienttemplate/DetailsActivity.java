package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codepath.apps.restclienttemplate.TweetAdapter.context;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvUserName) TextView tvUsername;
    @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;
    @BindView(R.id.tvRetweetCount) TextView tvRetweetCount;
    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
    @BindView(R.id.ivImg) ImageView ivImg;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.etReply) EditText etReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvBody.setText(tweet.body);
        tvUsername.setText(tweet.user.name);
        tvFavoriteCount.setText(tweet.favorites_count+" FAVORITES");
        tvRetweetCount.setText(tweet.retweet_count+" RETWEETS");
        tvTimeStamp.setText(tweet.timestamp);
        etReply.setHint("Reply to "+ tweet.user.name);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(ivProfileImage);
        if(tweet.url==""){
            ivImg.setVisibility(View.GONE);
        }
        else {
            ivImg.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.url)
                    .into(ivImg);
        }


    }
}
