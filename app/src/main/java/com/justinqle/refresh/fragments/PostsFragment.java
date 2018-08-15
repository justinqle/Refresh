package com.justinqle.refresh.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinqle.refresh.R;
import com.justinqle.refresh.architecture.PostAdapter;
import com.justinqle.refresh.architecture.PostViewModel;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class PostsFragment extends Fragment {

    private static final String TAG = "PostsFragment";

    private PostAdapter mAdapter;
    private PostViewModel postViewModel;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        // Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // TODO Different access tokens are retrieved twice on Authentication, due to data race between thread retrieving posts and thread retrieving subreddit items for nav menu
        Log.d(TAG, "Access Token: " + sharedPreferences.getString("access_token", null));
        Log.d(TAG, "Refresh Token: " + sharedPreferences.getString("refresh_token", null));
        Log.d(TAG, "Logged In: " + sharedPreferences.getBoolean("logged_in", false));

        // Swipe Container
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Initial refreshing
        swipeContainer.setRefreshing(true);

        // RecyclerView
        RecyclerView mRecyclerView = view.findViewById(R.id.my_recycler_view);
        // Changes in content does not change layout size
        mRecyclerView.setHasFixedSize(true);
        // LinearLayout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initial animation / refresh
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());

        // Hide FAB on scroll
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FloatingActionButton fab = getActivity().findViewById(R.id.fab);

                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        // Adapter
        mAdapter = new PostAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        // invalidate to loadInitial() again
        swipeContainer.setOnRefreshListener(() -> {
            // invalidate data source to force refresh
            postViewModel.refreshPosts();
        });

        // Observer Pattern: Will be used to observe changes to the Posts
        // Initially null, so loads posts and enacts observer
        // submit new set of data and set refreshing false
        postViewModel.getPosts().observe(this, posts -> {
            // TODO: Changing listing sometimes puts you in the middle (possibly due to DiffUtil)
            mAdapter.submitList(posts);
            swipeContainer.setRefreshing(false);
        });

        return view;
    }

}
