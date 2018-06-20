package com.justinqle.refresh.paging;

import android.arch.paging.DataSource;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.JSONPlaceHolderApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private JSONPlaceHolderApi jsonPlaceHolderApi;
    private PostDataSource postDataSource;

    public PostDataSourceFactory(JSONPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
    }

    @Override
    public DataSource<String, Post> create() {
        postDataSource = new PostDataSource(jsonPlaceHolderApi);
        return postDataSource;
    }

    public PostDataSource getPostDataSource() {
        return postDataSource;
    }
}
