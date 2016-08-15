package com.psd.tweets.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.fragments.ProfileFragment;
import com.psd.tweets.fragments.TimelineFragment;
import com.psd.tweets.models.User;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TweetsActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nvView) NavigationView nvDrawer;

    ActionBarDrawerToggle actionBarDrawerToggle;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        setupDrawerContent(nvDrawer);

        getSupportFragmentManager().beginTransaction().add(R.id.flContent, new TimelineFragment()).commit();

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
        switch(menuItem.getItemId()) {
            case R.id.nav_profile_fragment:
                TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        currentUser = User.fromJSONObject(response);
                        replaceWithFragment(ProfileFragment.newInstance(currentUser, true));
                    }
                });
                break;
        }

        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    public void replaceWithFragment(Fragment fragment) {
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.flContent, fragment);
        // or ft.add(R.id.flContent, fragment).addToBackStack("fragment");
        // Complete the changes added above
        ft.commit();
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

}
