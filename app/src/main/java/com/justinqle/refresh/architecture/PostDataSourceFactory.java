package com.justinqle.refresh.architecture;

import android.arch.paging.DataSource;

import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.networking.RedditApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private PostDataSource postDataSource;
    private RedditApi redditApi;
    private String subreddit;

    PostDataSourceFactory(RedditApi redditApi, String subreddit) {
        this.redditApi = redditApi;
        this.subreddit = subreddit;
    }

    @Override
    public DataSource<String, Post> create() {
        postDataSource = new PostDataSource(redditApi, subreddit);
        return postDataSource;
    }

    public void invalidate() {
        postDataSource.invalidate();
    }

}
