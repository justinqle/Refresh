package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.justinqle.refresh.MyApplication;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.pagination.DefaultPaginator;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class SubmissionsViewModel extends ViewModel {

    private MutableLiveData<RedditClient> redditClient;

    private LiveData<PagedList<Submission>> submissions;
    private SubmissionsDataSourceFactory factory;

    // Can define what kind of RedditClient we want on startup
    public LiveData<RedditClient> getRedditClient() {
        // Get userless RedditClient on startup
        if (redditClient == null) {
            redditClient = new MutableLiveData<>();
            loadRedditClient();
        }
        return redditClient;
    }

    private void loadRedditClient() {
        // Do an asynchronous operation to load RedditClient
        Single.fromCallable(() -> MyApplication.getAccountHelper().switchToUserless())
                .subscribeOn(Schedulers.io())
                .subscribe(newRedditClient -> {
                    // Posts value on background thread
                    this.redditClient.postValue(newRedditClient);
                });
    }

    public LiveData<PagedList<Submission>> getSubmissions(RedditClient redditClient) {
        // Initially null, so load submissions and call Observers
        if (submissions == null) {
            // Load default submissions on start up: frontpage
            loadSubmissions(redditClient.frontPage());
        }
        return submissions;
    }

    private void loadSubmissions(DefaultPaginator.Builder<Submission, SubredditSort> paginatorBuilder) {
        // Do an asynchronous operation to fetch submissions.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(50).setPageSize(25).build();
        factory = new SubmissionsDataSourceFactory(paginatorBuilder);
        submissions = new LivePagedListBuilder<>(factory, config).build();
    }

    // invalidate data source to force Factory to construct a new one
    public void invalidateDataSource() {
        if (submissions.getValue() != null) {
            submissions.getValue().getDataSource().invalidate();
        }
    }

    // Changes the factory's member variable, Paginator, and then invalidates the data source to force reconstruction of new data source with new Paginator
    public void changeDataSource(DefaultPaginator.Builder<Submission, SubredditSort> paginatorBuilder) {
        factory.setPaginationBuilder(paginatorBuilder);
        invalidateDataSource();
    }

}
