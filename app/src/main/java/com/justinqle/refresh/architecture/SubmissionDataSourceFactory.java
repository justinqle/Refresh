package com.justinqle.refresh.architecture;

import android.arch.paging.DataSource;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Sorting;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubmissionDataSourceFactory extends SubmissionDataSource.Factory<DefaultPaginator<Submission>, Submission> {

    private SubmissionDataSource submissionDataSource;
    private RedditClient redditClient;
    private Subreddit subreddit;
    private Sorting sorting;
    private TimePeriod timePeriod;

    public SubmissionDataSourceFactory(RedditClient redditClient, Subreddit subreddit, Sorting sorting, TimePeriod timePeriod) {
        this.redditClient = redditClient;
        this.subreddit = subreddit;
        this.sorting = sorting;
        this.timePeriod = timePeriod;
    }

    @Override
    public DataSource<DefaultPaginator<Submission>, Submission> create() {
        submissionDataSource = new SubmissionDataSource(redditClient, subreddit, sorting, timePeriod);
        return submissionDataSource;
    }

}
