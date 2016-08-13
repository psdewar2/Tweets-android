package com.psd.tweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.psd.tweets.R;
import com.psd.tweets.fragments.HomeFragment;
import com.psd.tweets.fragments.MentionsFragment;

public class TimelineActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    ViewPager viewPager;

    private ImageButton ibCompose;

    private static int COMPOSE_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Mentions"));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new TweetsFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        ibCompose = (ImageButton) findViewById(R.id.ibCompose);
        ibCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, COMPOSE_ACTIVITY_REQUEST);
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == resultCode) {
//            // for composing new tweets
//            String tweetBody = data.getStringExtra("tweet");
//            User tweetWrittenBy = Parcels.unwrap(data.getParcelableExtra("user"));
//            final Tweet newTweet = new Tweet();
//            newTweet.setBody(tweetBody);
//            newTweet.setUser(tweetWrittenBy);
//            tweets.add(0, newTweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.smoothScrollToPosition(0);
//            client.composeNewTweet(tweetBody, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
//                    int previousTweetIndex = tweets.indexOf(newTweet);
//                    tweets.set(previousTweetIndex, Tweet.fromJSON(json));
//                    tweetAdapter.notifyItemChanged(previousTweetIndex);
//
//                }
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    Log.e("Error", "code: " + statusCode);
//                }
//            });
//        }
//    }

    // PAGERADAPTER CLASS
    public static class TweetsFragmentPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public TweetsFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // HomeFragment
                    return HomeFragment.newInstance(0, "Home");
                case 1: // MentionsFragment
                    return MentionsFragment.newInstance(1, "Mentions");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
