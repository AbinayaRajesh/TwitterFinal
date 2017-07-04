package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;

public class ComposeActivity extends AppCompatActivity {

    long tweet_id;
    Boolean reply = false;
    String userId;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        reply = getIntent().getBooleanExtra("reply", false);
        tweet_id = getIntent().getLongExtra("tweet_id", -1);
        if (tweet_id!=-1) {
            String username = getIntent().getStringExtra("username");

            TextView tvReply = (TextView) findViewById(R.id.tvReply);
            userId = "@"+username;
            tvReply.setText(userId);
            reply = true;
        }



        final TextView mTextView = (TextView) findViewById(R.id.tvCharLeft);
        EditText mEditText = (EditText) findViewById(R.id.etTweet);

        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                //This sets a textview to the current length
                mTextView.setText(String.valueOf(140-s.length()));
            }
        };

        mEditText.addTextChangedListener(mTextEditorWatcher);

    }

    public void onCancel(View v) {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
    }


    // launched for a result
    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.etTweet);

        final String data = etName.getText().toString();

        TwitterClient client = new TwitterClient(this);


        }




}


