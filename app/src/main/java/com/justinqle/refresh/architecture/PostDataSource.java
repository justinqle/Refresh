package com.justinqle.refresh.architecture;

import android.arch.paging.PageKeyedDataSource;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.justinqle.refresh.models.Child;
import com.justinqle.refresh.models.Data;
import com.justinqle.refresh.models.Listing;
import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.RedditApi;
import com.justinqle.refresh.retrofit.TokenAuthenticatorApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<String, Post> {

    private static final String TAG = "PostDataSource";

    // pass whatever dependencies are needed to make the network call
    private RedditApi redditApi;
    private String accessToken;

    // define the type of data that will be emitted by this datasource
    PostDataSource(RedditApi redditApi) {
        this.redditApi = redditApi;
        this.accessToken = TokenAuthenticatorApp.getApplicationAccessTokenFromStorage();

        if (accessToken == null) {
            // Synchronous
            Log.d(TAG, "accessToken is null (first time opening app)");
            Log.d(TAG, "Getting new application-only access token and writing it to SharedPreferences");
            accessToken = TokenAuthenticatorApp.getApplicationAccessTokenSync();
        } else {
            Log.d(TAG, "accessToken retrieved from SharedPreferences (may be expired, taken care of by Authenticator)");
        }
    }

    private static void backgroundThreadLongToast(final String msg) {
        if (msg != null) {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getContextOfApplication(), msg, Toast.LENGTH_LONG).show());
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        Log.d(TAG, "(Load Initial) Access Token = " + accessToken);
        redditApi.getListing("bearer " + accessToken, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    Data data = listing.getData();
                    List<Child> children = data.getChildren();
                    List<Post> posts = new ArrayList<>();
                    for (Child child : children) {
                        posts.add(child.getData());
                    }
                    callback.onResult(posts, data.getBefore(), data.getAfter());
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load Initial: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast("Network request failed");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    // TODO Use rxJava, as this all runs on the main thread
    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        Log.d(TAG, "(Load After) Access Token = " + accessToken);
        redditApi.getListingAfter("bearer " + accessToken, params.key, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    Data data = listing.getData();
                    List<Child> children = data.getChildren();
                    List<Post> posts = new ArrayList<>();
                    for (Child child : children) {
                        posts.add(child.getData());
                    }
                    callback.onResult(posts, data.getAfter());
                } else {
                    Log.e(TAG, "HTTP Response Error " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load After: Network request failed");
                t.printStackTrace();
                backgroundThreadLongToast("Network request failed");
            }
        });
    }
}
