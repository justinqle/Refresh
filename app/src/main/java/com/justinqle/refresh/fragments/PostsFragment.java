package com.justinqle.refresh.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

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
                Activity activity = getActivity();
                if (activity != null) {
                    FloatingActionButton fab = getActivity().findViewById(R.id.fab);
                    if (dy > 0) {
                        fab.hide();
                    } else {
                        fab.show();
                    }
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

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {

        }
        // No special time options
        else if (id == R.id.best ||
                id == R.id.hot ||
                id == R.id.sort_new ||
                id == R.id.rising) {
            //changeListing(currentSubreddit.getText().toString(), item.getTitle().toString(), null);
        }
        // Special time options
        else if (item.getGroupId() == R.id.controversial) {
            //changeListing(currentSubreddit.getText().toString(), "Controversial", item.getTitle().toString());
        } else if (item.getGroupId() == R.id.top) {
            //changeListing(currentSubreddit.getText().toString(), "Top", item.getTitle().toString());
        } else if (id == R.id.change_view) {

        } else if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

}
