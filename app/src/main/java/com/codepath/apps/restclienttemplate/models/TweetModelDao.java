package com.codepath.apps.restclienttemplate.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TweetModelDao {
    @Query("SELECT * FROM tweetModel")
    List<TweetModel> getAll();

    @Query("SELECT * FROM TweetModel ORDER BY ID DESC LIMIT 25")
    List<SampleModel> recentItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(TweetModel... tweetModels);
}
