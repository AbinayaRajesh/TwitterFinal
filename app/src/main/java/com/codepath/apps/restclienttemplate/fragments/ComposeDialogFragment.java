package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
// ...

public class ComposeDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText etTweet;
    TextView tvCharLeft;
    Button btSubmit;
    Button btCancel;
    TwitterClient client;
    long tweet_id;
    boolean reply;
    String userId;
    ProgressBar pb;
    TextView tvReply;

    public ComposeDialogFragment() {}

    public interface ComposeDialogListener {
        void onFinishedTweet(Tweet tweet);
    }

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putBoolean("reply", false);
        frag.setArguments(args);
        return frag;
    }

    public static ComposeDialogFragment newInstance(String title, String screenName, long id) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("screenName", screenName);
        args.putLong("id", id);
        args.putBoolean("reply", true);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // on some click or some loading we need to wait for...
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.INVISIBLE);

        // Get field from view
        etTweet = (EditText) view.findViewById(R.id.etTweet);
        tvCharLeft = (TextView) view.findViewById(R.id.tvCharLeft);
        btSubmit = (Button) view.findViewById(R.id.tweet_action);
        btCancel = (Button) view.findViewById(R.id.cancel_action);
        tvReply = (TextView) view.findViewById(R.id.tvReply);
        client = TwitterApp.getRestClient();
        btSubmit.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        // title = getArguments().getString("title");
        userId = getArguments().getString("screenName");
        reply = getArguments().getBoolean("reply");

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("username", "Enter Name");
        getDialog().setTitle(title);
//
//        if (userId == null) reply = false;
//        else reply = true;

        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {
                tvCharLeft.setText(String.valueOf(140 - s.length()));
            }
        };

        etTweet.addTextChangedListener(mTextEditorWatcher);
        tweet_id = getArguments().getLong("id");

    }




    @Override
    public void onClick(View v) {
        pb.setVisibility(ProgressBar.VISIBLE);
        tvReply.setVisibility(View.GONE);


        if (v.getId() == R.id.tweet_action) {
            String data = etTweet.getText().toString();
            if (!reply) {
                client.sendTweet(data, (new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ComposeDialogListener listener = ((ComposeDialogListener) getActivity());
                        listener.onFinishedTweet(tweet);
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        tvReply.setVisibility(View.VISIBLE);
                        dismiss();

                    }
                }));

            } else {
                client.reply("@" + userId + " " + data, tweet_id, (new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ComposeDialogListener listener = (ComposeDialogListener) getActivity();
                        listener.onFinishedTweet(tweet);
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        tvReply.setVisibility(View.VISIBLE);
                        dismiss();
                    }
                }));
            }
        }
        else if (v.getId() == R.id.cancel_action){
            dismiss();
        }


    }
}