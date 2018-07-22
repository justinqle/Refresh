package com.justinqle.refresh.architecture;

import android.arch.paging.DataSource;

import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.networking.RedditApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private PostDataSource postDataSource;
    private RedditApi redditApi;
    private String subreddit;
    private String sort;
    private String time;

    PostDataSourceFactory(RedditApi redditApi, String subreddit, String sort, String time) {
        this.redditApi = redditApi;
        this.subreddit = subreddit;
        this.sort = sort;
        this.time = time;
    }

    @Override
    public DataSource<String, Post> create() {
        postDataSource = new PostDataSource(redditApi, subreddit, sort, time);
        return postDataSource;
    }

    public void invalidate() {
        postDataSource.invalidate();
    }

}
