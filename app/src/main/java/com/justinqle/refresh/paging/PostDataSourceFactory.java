package com.justinqle.refresh.paging;

import android.arch.paging.DataSource;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.RedditApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private RedditApi redditApi;
    private PostDataSource postDataSource;

    public PostDataSourceFactory(RedditApi redditApi) {
        this.redditApi = redditApi;
    }

    @Override
    public DataSource<String, Post> create() {
        postDataSource = new PostDataSource(redditApi);
        return postDataSource;
    }

    public PostDataSource getPostDataSource() {
        return postDataSource;
    }
}
