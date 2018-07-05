package com.justinqle.refresh.architecture;

import android.arch.paging.PageKeyedDataSource;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.justinqle.refresh.activities.MainActivity;
import com.justinqle.refresh.models.listing.Child;
import com.justinqle.refresh.models.listing.Data;
import com.justinqle.refresh.models.listing.Listing;
import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.retrofit.RedditApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<String, Post> {

    private static final String TAG = "PostDataSource";

    // pass whatever dependencies are needed to make the network call
    private RedditApi redditApi;

    // define the type of data that will be emitted by this datasource
    PostDataSource(RedditApi redditApi) {
        this.redditApi = redditApi;
    }

    /**
     * Notify Main thread of failed Network request.
     */
    private static void backgroundThreadLongToast() {
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getContextOfApplication(), "Network request failed", Toast.LENGTH_LONG).show());
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        redditApi.getListing(params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    if (listing != null) {
                        Data data = listing.getData();
                        List<Child> children = data.getChildren();
                        List<Post> posts = new ArrayList<>();
                        for (Child child : children) {
                            posts.add(child.getData());
                        }
                        callback.onResult(posts, data.getBefore(), data.getAfter());
                    }
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load Initial: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    // TODO Use rxJava, as this all runs on the overflow thread
    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        redditApi.getListingAfter(params.key, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    if (listing != null) {
                        Data data = listing.getData();
                        List<Child> children = data.getChildren();
                        List<Post> posts = new ArrayList<>();
                        for (Child child : children) {
                            posts.add(child.getData());
                        }
                        callback.onResult(posts, data.getAfter());
                    }
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load After: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast();
            }
        });
    }
}
