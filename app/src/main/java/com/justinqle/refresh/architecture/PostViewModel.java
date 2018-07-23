package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.networking.NetworkService;

public class PostViewModel extends ViewModel {

    private LiveData<PagedList<Post>> posts;

    public LiveData<PagedList<Post>> getPosts() {
        // Initially null, so load posts and call Observers
        if (posts == null) {
            loadPosts(null, "best", null);
        }
        return posts;
    }

    public LiveData<PagedList<Post>> getNewPosts(String subreddit, String sort, String time) {
        loadPosts(subreddit, sort, time);
        return posts;
    }

    private void loadPosts(String subreddit, String sort, String time) {
        // Do an asynchronous operation to fetch posts.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(50).setPageSize(25).build();
        PostDataSourceFactory factory = new PostDataSourceFactory(NetworkService.getInstance().getJSONApi(), subreddit, sort, time);
        posts = new LivePagedListBuilder<>(factory, config).build();
    }

    public void refreshPosts() {
        if (posts.getValue() != null) {
            posts.getValue().getDataSource().invalidate();
        }
    }

}
