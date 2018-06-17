package com.justinqle.refresh.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.JSONPlaceHolderApi;

public class PostDataSourceFactory extends PostDataSource.Factory<String, Post> {

    private JSONPlaceHolderApi jsonPlaceHolderApi;
    // reference to data source to invalidate data source to force a refresh
    public MutableLiveData<PostDataSource> postLiveData;

    public PostDataSourceFactory(JSONPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
    }

    @Override
    public DataSource<String, Post> create() {
        PostDataSource dataSource = new PostDataSource(jsonPlaceHolderApi);

        // keep reference to the data source with a MutableLiveData reference
        postLiveData = new MutableLiveData<>();
        postLiveData.postValue(dataSource);

        return dataSource;
    }
}
