package com.justinqle.refresh.architecture;

import android.arch.paging.DataSource;

import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.pagination.DefaultPaginator;

public class SubmissionsDataSourceFactory extends SubmissionsDataSource.Factory<DefaultPaginator<Submission>, Submission> {

    private DefaultPaginator.Builder<Submission, SubredditSort> paginationBuilder;

    SubmissionsDataSourceFactory(DefaultPaginator.Builder<Submission, SubredditSort> paginationBuilder) {
        this.paginationBuilder = paginationBuilder;
    }

    @Override
    public DataSource<DefaultPaginator<Submission>, Submission> create() {
        return new SubmissionsDataSource(paginationBuilder);
    }

    void setPaginationBuilder(DefaultPaginator.Builder<Submission, SubredditSort> paginationBuilder) {
        this.paginationBuilder = paginationBuilder;
    }

}
