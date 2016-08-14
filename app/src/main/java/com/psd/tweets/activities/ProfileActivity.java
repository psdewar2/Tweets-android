package com.psd.tweets.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.TwitterClient;
import com.psd.tweets.adapters.TweetAdapter;
import com.psd.tweets.databinding.ActivityProfileBinding;
import com.psd.tweets.models.Tweet;
import com.psd.tweets.models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by PSD on 8/13/16.
 */
public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private TwitterClient client;
    private ArrayList<Tweet> userTweets;
    private TweetAdapter tweetAdapter;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        String profileImageUrl = user.getProfileImageUrl();
        if (!TextUtils.isEmpty(profileImageUrl)) {
            Glide.with(this).load(profileImageUrl.replace("_normal", "_bigger"))
                    .bitmapTransform(new RoundedCornersTransformation(this, 5, 0))
                    .into(binding.ivUser);
        }
        binding.tvUserName.setText(user.getName());
        binding.tvUserScreenName.setText(String.valueOf("@" + user.getScreenName()));
        binding.tvDescription.setText(user.getDescription());
        binding.tvFollowingValue.setText(String.valueOf(user.getFriendsCount()));
        binding.tvFollowersValue.setText(String.valueOf(user.getFollowersCount()));

        // create ArrayList
        userTweets = new ArrayList<>();
        // construct adapter from data source
        tweetAdapter = new TweetAdapter(this, userTweets);
        // get Layout Manager
        final LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // connect adapter to RecyclerView
        binding.rvUserTweets.setLayoutManager(llm);
        binding.rvUserTweets.setHasFixedSize(true);
        binding.rvUserTweets.setAdapter(tweetAdapter);
        // retrieve client from TwitterApplication (will be used for every activity)
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline();
    }

    private void populateTimeline() {
        boolean isCurrentUser = getIntent().getBooleanExtra("currentUser", false);
        if (isCurrentUser) {
            client.getUserTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    Log.d("DEBUG", json.toString());
                    userTweets.clear();
                    userTweets.addAll(Tweet.fromJSONArray(json));
                    tweetAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ERROR", "code: " + statusCode);
                }
            });
        } else {
            client.showSelectedUserTimeline(user, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    Log.d("selected user", json.toString());
                    userTweets.clear();
                    userTweets.addAll(Tweet.fromJSONArray(json));
                    tweetAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("ERROR", "code: " + statusCode);
                }
            });
        }

    }
}
