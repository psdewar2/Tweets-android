package com.psd.tweets.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.TwitterClient;
import com.psd.tweets.adapters.TweetAdapter;
import com.psd.tweets.models.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by PSD on 8/12/16.
 */
public class MentionsFragment extends Fragment {
    private TwitterClient client;
    private ArrayList<Tweet> mentions;
    private TweetAdapter mentionsAdapter;

    public static MentionsFragment newInstance(int tab, String title) {
        MentionsFragment mentionsFragment = new MentionsFragment();
        Bundle args = new Bundle();
        args.putInt("tab", tab);
        mentionsFragment.setArguments(args);
        return mentionsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_mentions, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // find RecyclerView
        RecyclerView rvMentions = (RecyclerView) view.findViewById(R.id.rvMentions);
        // create ArrayList
        mentions = new ArrayList<>();
        // construct adapter from data source
        mentionsAdapter = new TweetAdapter(getContext(), mentions);
        // get Layout Manager
        final LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        // connect adapter to RecyclerView
        rvMentions.setLayoutManager(llm);
        rvMentions.setHasFixedSize(true);
        rvMentions.setAdapter(mentionsAdapter);
        // retrieve client from TwitterApplication (will be used for every activity)
        client = TwitterApplication.getRestClient(); //singleton client

//        rvMentions.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                loadMoreTweets(page);
//            }
//        });

        populateTimeline();
    }

    private void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                mentions.clear();
                mentions.addAll(Tweet.fromJSONArray(json));
                mentionsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", "code: " + statusCode);
            }
        });
    }
}
