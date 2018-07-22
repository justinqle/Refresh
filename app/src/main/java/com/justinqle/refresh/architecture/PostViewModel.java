package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.networking.NetworkService;

public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    private LiveData<PagedList<Post>> posts;
    private PostDataSourceFactory factory;

    public LiveData<PagedList<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<>();
            loadPosts(null, "best", null);
        }
        return posts;
    }

    public LiveData<PagedList<Post>> getNewPosts(String subreddit, String sort, String time) {
        posts = new MutableLiveData<>();
        loadPosts(subreddit, sort, time);
        return posts;
    }

    private void loadPosts(String subreddit, String sort, String time) {
        // Do an asynchronous operation to fetch posts.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(50).setPageSize(25).build();
        factory = new PostDataSourceFactory(NetworkService.getInstance().getJSONApi(), subreddit, sort, time);
        posts = new LivePagedListBuilder<>(factory, config).build();
    }

    public void refreshPosts() {
        factory.invalidate();
    }

}
