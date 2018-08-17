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

public class SubmissionsViewModel extends ViewModel {

    private LiveData<PagedList<Submission>> submissions;

    public LiveData<PagedList<Submission>> getSubmissions() {
        // Initially null, so load submissions and call Observers
        if (submissions == null) {
            loadPosts(null, null, null, null);
        }
        return submissions;
    }

//    public LiveData<PagedList<Submission>> getNewPosts(String subreddit, String sort, String time) {
//        loadPosts(subreddit, sort, time);
//        return submissions;
//    }

    private void loadPosts(RedditClient redditClient, Subreddit subreddit, Sorting sorting, TimePeriod timePeriod) {
        // Do an asynchronous operation to fetch submissions.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(50).setPageSize(25).build();
        SubmissionsDataSourceFactory factory = new SubmissionsDataSourceFactory(redditClient, subreddit, sorting, timePeriod);
        submissions = new LivePagedListBuilder<>(factory, config).build();
    }

    public void refreshPosts() {
        if (submissions.getValue() != null) {
            submissions.getValue().getDataSource().invalidate();
        }
    }

}
