package com.psd.tweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.EndlessRecyclerViewScrollListener;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.TwitterClient;
import com.psd.tweets.activities.ComposeActivity;
import com.psd.tweets.adapters.TweetAdapter;
import com.psd.tweets.models.Tweet;
import com.psd.tweets.models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by PSD on 8/12/16.
 */
public class HomeFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetAdapter tweetAdapter;
    RecyclerView rvTweets;

    public static HomeFragment newInstance(int tab, String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("tab", tab);
        args.putString("title", title);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // find RecyclerView
        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
        // create ArrayList
        tweets = new ArrayList<>();
        // construct adapter from data source
        tweetAdapter = new TweetAdapter(getContext(), tweets);
        int hmm = tweets.size() - 1;
        Log.d("item count: ",  + tweetAdapter.getItemCount() + " to " + hmm);
        // get Layout Manager
        final LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComposeActivity.class);
                startActivityForResult(i, 1);
            }
        });

        populateTimeline();
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
                Log.e("ERROR", "code: " + statusCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
