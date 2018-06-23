package com.justinqle.refresh.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.NetworkService;

public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    private LiveData<PagedList<Post>> posts;
    private PostDataSourceFactory factory;

    public LiveData<PagedList<Post>> getPosts() {
        if (posts == null) {
            posts = new MutableLiveData<>();
            loadPosts();
        }
        return posts;
    }

    private void loadPosts() {
        // Do an asynchronous operation to fetch posts.
        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(25).setPageSize(25).build();
        factory = new PostDataSourceFactory(NetworkService.getInstance().getJSONApi());
        posts = new LivePagedListBuilder(factory, config).build();
    }

    public void invalidateDataSource() {
        factory.getPostDataSource().invalidate();
    }

}
