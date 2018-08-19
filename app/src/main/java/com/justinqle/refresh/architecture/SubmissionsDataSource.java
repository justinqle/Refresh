package com.justinqle.refresh.architecture;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubmissionsDataSource extends PageKeyedDataSource<DefaultPaginator<Submission>, Submission> {

    private DefaultPaginator.Builder<Submission, SubredditSort> paginatorBuilder;

    // define the type of data that will be emitted by this data source
    SubmissionsDataSource(DefaultPaginator.Builder<Submission, SubredditSort> paginatorBuilder) {
        this.paginatorBuilder = paginatorBuilder;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<DefaultPaginator<Submission>> params, @NonNull LoadInitialCallback<DefaultPaginator<Submission>, Submission> callback) {
        // Builds paginator with requested load size specified by PagedList.Config
        DefaultPaginator<Submission> paginator = paginatorBuilder.limit(params.requestedLoadSize).build();
        // Grabs first page Listing
        // TODO: try/catch to handle paginator.next() exceptions e.g. auth error (401)
        Listing<Submission> firstPage = paginator.next();
        // Passes the paginator to loadAfter()
        callback.onResult(firstPage, null, paginator);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<DefaultPaginator<Submission>> params, @NonNull LoadCallback<DefaultPaginator<Submission>, Submission> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<DefaultPaginator<Submission>> params, @NonNull LoadCallback<DefaultPaginator<Submission>, Submission> callback) {
        // Grabs next page from paginator and passes paginator to next loadAfter()
        callback.onResult(params.key.next(), params.key);
    }

}
