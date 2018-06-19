package com.justinqle.refresh.paging;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.justinqle.refresh.MainActivity;
import com.justinqle.refresh.models.Child;
import com.justinqle.refresh.models.Data;
import com.justinqle.refresh.models.Listing;
import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.JSONPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<String, Post> {

    // pass whatever dependencies are needed to make the network call
    private JSONPlaceHolderApi jsonPlaceHolderApi;

    private String accessToken;
    private String refreshToken;

    // define the type of data that will be emitted by this datasource
    PostDataSource(JSONPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;

        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("oauth", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
        refreshToken = sharedPreferences.getString("refresh_token", null);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        jsonPlaceHolderApi.getListing("bearer " + accessToken, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                Data data = response.body().getData();
                List<Post> posts = new ArrayList<>();
                for (Child child : data.getChildren()) {
                    posts.add(child.getData());
                }
                callback.onResult(posts, data.getBefore(), data.getAfter());
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                t.printStackTrace();
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
        jsonPlaceHolderApi.getListingAfter("bearer " + accessToken, params.key, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                if (response.isSuccessful()) {
                    Data data = response.body().getData();
                    List<Post> posts = new ArrayList<>();
                    for (Child child : data.getChildren()) {
                        posts.add(child.getData());
                    }
                    callback.onResult(posts, data.getAfter());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
