package com.psd.tweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PSD on 8/1/16.
 */
public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

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

    public User(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.uid = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
