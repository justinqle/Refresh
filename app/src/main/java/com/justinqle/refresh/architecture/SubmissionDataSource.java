package com.justinqle.refresh.architecture;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.justinqle.refresh.MyApplication;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Sorting;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubmissionDataSource extends PageKeyedDataSource<DefaultPaginator<Submission>, Submission> {

    private static final String TAG = "SubmissionDataSource";

    private RedditClient redditClient;
    private Subreddit subreddit;
    private Sorting sorting;
    private TimePeriod timePeriod;

    // define the type of data that will be emitted by this datasource
    SubmissionDataSource(RedditClient redditClient, Subreddit subreddit, Sorting sorting, TimePeriod timePeriod) {
        this.redditClient = redditClient;
        this.subreddit = subreddit;
        this.sorting = sorting;
        this.timePeriod = timePeriod;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<DefaultPaginator<Submission>> params, @NonNull LoadInitialCallback<DefaultPaginator<Submission>, Submission> callback) {
        DefaultPaginator<Submission> paginator = MyApplication.getAccountHelper().switchToUserless().frontPage().limit(params.requestedLoadSize).build();
        Listing<Submission> firstPage = paginator.next();
        callback.onResult(firstPage, null, paginator);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<DefaultPaginator<Submission>> params, @NonNull LoadCallback<DefaultPaginator<Submission>, Submission> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<DefaultPaginator<Submission>> params, @NonNull LoadCallback<DefaultPaginator<Submission>, Submission> callback) {
        callback.onResult(params.key.next(), params.key);
    }

}
