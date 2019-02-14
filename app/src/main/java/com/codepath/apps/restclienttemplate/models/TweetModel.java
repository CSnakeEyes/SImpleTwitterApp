package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
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
    @ColumnInfo(name = "uuid")
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid){
        this.uid = uid;
    }

    @ColumnInfo(name = "createdAt")
    private String createdAt;

    public String getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    @ColumnInfo(name = "body")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Embedded User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ColumnInfo
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

}
