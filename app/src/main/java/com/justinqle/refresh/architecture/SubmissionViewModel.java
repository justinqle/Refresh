package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Sorting;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.TimePeriod;

public class SubmissionViewModel extends ViewModel {

    private LiveData<PagedList<Submission>> posts;

    public LiveData<PagedList<Submission>> getPosts() {
        // Initially null, so load posts and call Observers
        if (posts == null) {
            loadPosts(null, null, null, null);
        }
        return posts;
    }

//    public LiveData<PagedList<Submission>> getNewPosts(String subreddit, String sort, String time) {
//        loadPosts(subreddit, sort, time);
//        return posts;
//    }

    private void loadPosts(RedditClient redditClient, Subreddit subreddit, Sorting sorting, TimePeriod timePeriod) {
        // Do an asynchronous operation to fetch posts.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(50).setPageSize(25).build();
        SubmissionDataSourceFactory factory = new SubmissionDataSourceFactory(redditClient, subreddit, sorting, timePeriod);
        posts = new LivePagedListBuilder<>(factory, config).build();
    }

    public void refreshPosts() {
        if (posts.getValue() != null) {
            posts.getValue().getDataSource().invalidate();
        }
    }

}
