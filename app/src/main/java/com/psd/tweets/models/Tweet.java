package com.psd.tweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PSD on 8/1/16.
 */
public class Tweet {
    // 1. Parse JSON + store data
    // 2. Encapsulate state logic or display logic

    private String body;
    private long uid; //unique, NOT user, id for the tweet
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    // Constructor that deserializes the JSON to create Tweet objects
    public Tweet(JSONObject jsonObject) {
        try {
            this.body = jsonObject.getString("text");
            this.uid = jsonObject.getLong("id");
            this.createdAt = jsonObject.getString("created_at");
            this.user = new User(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // pass in a JSONArray of items and convert into an ArrayList of Tweets
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> results = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Tweet(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}
