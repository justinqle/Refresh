package com.justinqle.refresh.paging;

import android.arch.paging.DataSource;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.JSONPlaceHolderApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private JSONPlaceHolderApi jsonPlaceHolderApi;

    public PostDataSourceFactory(JSONPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
    }

    // TODO add support for SwipeForRefresh
    @Override
    public DataSource<String, Post> create() {
        return new PostDataSource(this.jsonPlaceHolderApi);
    }
}
