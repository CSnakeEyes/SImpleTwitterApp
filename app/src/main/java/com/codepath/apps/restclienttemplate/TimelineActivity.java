package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetModel;
import com.codepath.apps.restclienttemplate.models.TweetModelDao;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    private TwitterClient client;
    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;
    private TweetModelDao tweetModelDao;

    private SwipeRefreshLayout swipeContainer;
//    tweetModelDao = ((TwitterApp) getApplicationContext()).getMyDatabase().tweetModelDao();

    private AsyncTask<TweetModel, Void, Void> task =  new AsyncTask<TweetModel, Void, Void>() {
        @Override
        protected Void doInBackground(TweetModel... tweetModel) {
            tweetModelDao.insertModel(tweetModel);
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        // Get the DAO
        tweetModelDao = ((TwitterApp) getApplicationContext()).getMyDatabase().tweetModelDao();

        swipeContainer = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Find RecyclerView
        rvTweets = findViewById(R.id.tweets_rv);

        // Initialize both adapter and list of tweets
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);

        // Setup RecyclerView: set up both layout manager and adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);

        populateHomeTimeline();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TwitterClient", "onRefresh: Success!");
                populateHomeTimeline();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.compose_menu) {
            // If 'Compose' icon selected then...
//            Toast.makeText(this, "Compose!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComposeFragment.class);
//            this.startActivityForResult(intent, REQUEST_CODE);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, tweet);
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }
    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.d("TwitterClient", response.toString());

                List<Tweet> tweetsToAdd = new ArrayList<>();
                // Iterate over JSONArray
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonTweetObject = response.getJSONObject(i);

                        // Convert each JSONObject into a Tweet object
                        Tweet tweet = Tweet.fromJson(jsonTweetObject);

                        tweetsToAdd.add(tweet);

                        // Add tweet to local database
                        final TweetModel tweetModel = TweetModel.fromJson(jsonTweetObject);
                        task.execute(tweetModel);
//                        ((TwitterApp) getApplicationContext()).getMyDatabase().runInTransaction(new Runnable() {
//                            @Override
//                            public void run() {
//                                tweetModelDao.insertModel(tweetModel);
//                            }
//                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.clear();
                adapter.addTweets(tweetsToAdd);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("TwitterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TwitterClient", responseString);
            }
        });
    }
}
