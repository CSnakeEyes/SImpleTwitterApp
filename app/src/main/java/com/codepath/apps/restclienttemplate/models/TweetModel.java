package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class TweetModel {

    public TweetModel() {
    }

    public static TweetModel fromJson(JSONObject jsonObject) throws JSONException {
        TweetModel tweetModel = new TweetModel();
        tweetModel.body = jsonObject.getString("text");
        tweetModel.uid = jsonObject.getLong("id");
        tweetModel.createdAt = jsonObject.getString("created_at");
        tweetModel.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweetModel;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    private long uid;

    @NonNull
    public long getUid() {
        return uid;
    }

    public void setUid(long uid){
        this.uid = uid;
    }

    @NonNull
    @ColumnInfo(name = "createdAt")
    private String createdAt;

    @NonNull
    public String getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    @NonNull
    @ColumnInfo(name = "body")
    private String body;

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @NonNull
    @ColumnInfo(name = "user")
    private User user;

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
