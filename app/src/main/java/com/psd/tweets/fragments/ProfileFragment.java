package com.psd.tweets.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.EndlessRecyclerViewScrollListener;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.TwitterClient;
import com.psd.tweets.activities.TweetsActivity;
import com.psd.tweets.adapters.TweetAdapter;
import com.psd.tweets.databinding.FragmentProfileBinding;
import com.psd.tweets.models.Tweet;
import com.psd.tweets.models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    private TwitterClient client;
    private ArrayList<Tweet> userTweets;
    private TweetAdapter tweetAdapter;

    User user;

    public static ProfileFragment newInstance(User user, boolean isCurrent) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        args.putBoolean("current", isCurrent);
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        user = Parcels.unwrap(getArguments().getParcelable("user"));
        ((TweetsActivity) getActivity()).getSupportActionBar().setTitle(user.getName());
        String profileImageUrl = user.getProfileImageUrl();
        if (!TextUtils.isEmpty(profileImageUrl)) {
            Glide.with(getContext()).load(profileImageUrl.replace("_normal", "_bigger"))
                    .bitmapTransform(new RoundedCornersTransformation(getContext(), 5, 0))
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
        tweetAdapter = new TweetAdapter(getContext(), userTweets);
        // get Layout Manager
        final LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        // connect adapter to RecyclerView
        binding.rvUserTweets.setLayoutManager(llm);
        binding.rvUserTweets.setHasFixedSize(true);
        binding.rvUserTweets.setAdapter(tweetAdapter);
        // retrieve client from TwitterApplication (will be used for every activity)
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline();

        binding.rvUserTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreTweets(page);
            }
        });

    }

    private void populateTimeline() {
        boolean isCurrentUser = getArguments().getBoolean("current", false);
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

    private void loadMoreTweets(int page) {
        Log.d("DEBUG", "page " + page);
        client.getUserTimeline(user.getScreenName(), page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                userTweets.addAll(Tweet.fromJSONArray(json));
                tweetAdapter.notifyItemRangeInserted(tweetAdapter.getItemCount(), userTweets.size() - 1);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", "code: " + statusCode);
            }
        });
    }
}
