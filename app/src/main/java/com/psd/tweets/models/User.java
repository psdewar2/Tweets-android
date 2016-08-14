package com.psd.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by PSD on 8/1/16.
 */
@Parcel
public class User {
    public static User currentUser; //to be used later

    //picture, tagline, # of followers, # of following, and tweets on their profile

    private String description;
    private int followersCount; // followers
    private int friendsCount; // following
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getDescription() { return description; }

    public int getFollowersCount() { return followersCount; }

    public int getFriendsCount() { return friendsCount; }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public User() {}

    public User(JSONObject jsonObject) {
        try {
            this.description = jsonObject.getString("description");
            this.followersCount = jsonObject.getInt("followers_count");
            this.friendsCount = jsonObject.getInt("friends_count");
            this.name = jsonObject.getString("name");
            this.uid = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static User fromJSONObject(JSONObject json) {
        return new User(json);
    }

}
