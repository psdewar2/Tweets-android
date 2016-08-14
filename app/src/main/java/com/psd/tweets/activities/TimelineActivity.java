package com.psd.tweets.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.fragments.HomeFragment;
import com.psd.tweets.fragments.MentionsFragment;
import com.psd.tweets.fragments.ProfileFragment;
import com.psd.tweets.models.User;

import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.nvView) NavigationView nvDrawer;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    TweetsFragmentPagerAdapter adapterViewPager;
    ActionBarDrawerToggle actionBarDrawerToggle;

    User currentUser;

    private static int COMPOSE_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        setupDrawerContent(nvDrawer);

        adapterViewPager = new TweetsFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // listeners
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String tabTitles[] = {"Home", "Mentions"};
                toolbar.setTitle(tabTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, COMPOSE_ACTIVITY_REQUEST);
            }
        });

    }

    private void setupDrawerContent(NavigationView navigationView) {
        nvDrawer.getMenu().getItem(0).setTitle("My Profile");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        currentUser = User.fromJSONObject(response);
                        Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
                        i.putExtra("currentUser", true);
                        i.putExtra("user", Parcels.wrap(currentUser));
                        startActivity(i);

                    }
                });

//                fragmentClass = ProfileFragment.class;
                break;
            default:
                fragmentClass = ProfileFragment.class;
        }

        try {
//            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
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
        private String tabTitles[] = new String[] { "Home", "Mentions"};

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
            return tabTitles[position];
        }

    }

}
