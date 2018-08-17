package com.justinqle.refresh.architecture;

import android.arch.paging.DataSource;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Sorting;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.DefaultPaginator;

public class PostDataSourceFactory extends PostDataSource.Factory<DefaultPaginator<Submission>, Submission> {

    private PostDataSource postDataSource;
    private RedditClient redditClient;
    private Subreddit subreddit;
    private Sorting sorting;
    private TimePeriod timePeriod;

    public PostDataSourceFactory(RedditClient redditClient, Subreddit subreddit, Sorting sorting, TimePeriod timePeriod) {
        this.redditClient = redditClient;
        this.subreddit = subreddit;
        this.sorting = sorting;
        this.timePeriod = timePeriod;
    }

    @Override
    public DataSource<DefaultPaginator<Submission>, Submission> create() {
        postDataSource = new PostDataSource(redditClient, subreddit, sorting, timePeriod);
        return postDataSource;
    }

}
