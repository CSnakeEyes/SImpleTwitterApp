package com.codepath.apps.restclienttemplate;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.codepath.apps.restclienttemplate.models.TweetModel;
import com.codepath.apps.restclienttemplate.models.TweetModelDao;

@Database(entities={TweetModel.class}, version=4)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TweetModelDao tweetModelDao();

    // Database name to be used
    public static final String NAME = "tweetDb";
}
