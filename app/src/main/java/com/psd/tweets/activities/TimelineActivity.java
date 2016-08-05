package com.psd.tweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.EndlessRecyclerViewScrollListener;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.TwitterClient;
import com.psd.tweets.adapters.TweetAdapter;
import com.psd.tweets.models.Tweet;
import com.psd.tweets.models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private ImageButton ibCompose;
    private RecyclerView rvTweets;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetAdapter tweetAdapter;

    private static int COMPOSE_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ibCompose = (ImageButton) findViewById(R.id.ibCompose);
        // find RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        // create ArrayList
        tweets = new ArrayList<>();
        // construct adapter from data source
        tweetAdapter = new TweetAdapter(this, tweets);
        int hmm = tweets.size() - 1;
        Log.d("item count: ",  + tweetAdapter.getItemCount() + " to " + hmm);
        // get Layout Manager
        final LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // connect adapter to RecyclerView
        rvTweets.setLayoutManager(llm);
        rvTweets.setHasFixedSize(true);
        rvTweets.setAdapter(tweetAdapter);
        // retrieve client from TwitterApplication (will be used for every activity)
        client = TwitterApplication.getRestClient(); //singleton client

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreTweets(page);
            }
        });
        ibCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, COMPOSE_ACTIVITY_REQUEST);
            }
        });

        populateTimeline();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            // for composing new tweets
            String tweetBody = data.getStringExtra("tweet");
            User tweetWrittenBy = Parcels.unwrap(data.getParcelableExtra("user"));
            final Tweet newTweet = new Tweet();
            newTweet.setBody(tweetBody);
            newTweet.setUser(tweetWrittenBy);
            tweets.add(0, newTweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
            client.composeNewTweet(tweetBody, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    int previousTweetIndex = tweets.indexOf(newTweet);
                    tweets.set(previousTweetIndex, Tweet.fromJSON(json));
                    tweetAdapter.notifyItemChanged(previousTweetIndex);

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("Error", "code: " + statusCode);
                }
            });
        }
    }



    // send an API request to get the timeline json
    // fill RecyclerView by creating the tweet objects from the json
    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                tweets.clear();
                tweets.addAll(Tweet.fromJSONArray(json));
                tweetAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", "code: " + statusCode);
            }
        });
    }

    private void loadMoreTweets(int page) {
        Log.d("DEBUG", "page " + page);
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                tweets.addAll(Tweet.fromJSONArray(json));
                tweetAdapter.notifyItemRangeInserted(tweetAdapter.getItemCount(), tweets.size() - 1);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", errorResponse.toString());
            }
        });
    }
}
