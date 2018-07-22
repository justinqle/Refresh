package com.justinqle.refresh.architecture;

import android.arch.paging.PageKeyedDataSource;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.justinqle.refresh.MyApplication;
import com.justinqle.refresh.models.listing.Child;
import com.justinqle.refresh.models.listing.Data;
import com.justinqle.refresh.models.listing.Listing;
import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.networking.RedditApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<String, Post> {

    private static final String TAG = "PostDataSource";

    // pass whatever dependencies are needed to make the network call
    private RedditApi redditApi;
    // null if frontpage listing
    private String subreddit;
    private String sort;
    // null if "controversial" or "top" sort
    private String time;

    // define the type of data that will be emitted by this datasource
    PostDataSource(RedditApi redditApi, String subreddit, String sort, String time) {
        this.redditApi = redditApi;
        this.subreddit = subreddit;
        this.sort = sort;
        this.time = time;
    }

    /**
     * Notify Main thread of failed Network request.
     */
    private static void backgroundThreadLongToast() {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MyApplication.getContext(), "Network request failed", Toast.LENGTH_LONG).show());
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        Callback<Listing> retrofitCallback = new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    if (listing != null) {
                        Data data = listing.getData();
                        List<Child> children = data.getChildren();
                        List<Post> posts = new ArrayList<>();
                        for (Child child : children) {
                            posts.add((Post) child.getData());
                        }
                        callback.onResult(posts, data.getBefore(), data.getAfter());
                    }
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                    backgroundThreadLongToast();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                Log.e(TAG, "Load Initial: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast();
            }
        };
        // frontpage listing
        if (subreddit == null) {
            redditApi.getFrontpageListing(sort, time, params.requestedLoadSize, null).enqueue(retrofitCallback);
        }
        // subreddit listing
        else {
            redditApi.getSubredditListing(subreddit, sort, time, params.requestedLoadSize, null).enqueue(retrofitCallback);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        Callback<Listing> retrofitCallback = new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    if (listing != null) {
                        Data data = listing.getData();
                        List<Child> children = data.getChildren();
                        List<Post> posts = new ArrayList<>();
                        for (Child child : children) {
                            posts.add((Post) child.getData());
                        }
                        callback.onResult(posts, data.getAfter());
                    }
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                    backgroundThreadLongToast();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                Log.e(TAG, "Load After: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast();
            }
        };
        // frontpage listing
        if (subreddit == null) {
            redditApi.getFrontpageListing(sort, time, params.requestedLoadSize, params.key).enqueue(retrofitCallback);
        }
        // subreddit listing
        else {
            redditApi.getSubredditListing(subreddit, sort, time, params.requestedLoadSize, params.key).enqueue(retrofitCallback);
        }
    }
}
