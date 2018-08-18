package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.justinqle.refresh.MyApplication;

import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubmissionsViewModel extends ViewModel {

    private LiveData<PagedList<Submission>> submissions;
    private SubmissionsDataSourceFactory factory;

    public LiveData<PagedList<Submission>> getSubmissions() {
        // Initially null, so load submissions and call Observers
        if (submissions == null) {
            // Load default submissions on start up: frontpage
            loadSubmissions(MyApplication.getAccountHelper().getReddit().frontPage());
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
