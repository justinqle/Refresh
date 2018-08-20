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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.justinqle.refresh.R;
import com.justinqle.refresh.architecture.SubmissionsAdapter;
import com.justinqle.refresh.architecture.SubmissionsViewModel;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;

public class SubmissionsFragment extends Fragment {

    private SubmissionsAdapter mAdapter;
    private SubmissionsViewModel submissionsViewModel;
    private SwipeRefreshLayout swipeContainer;
    private RedditClient redditClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submissions, container, false);

        // Swipe Container
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Initial refreshing
        swipeContainer.setRefreshing(true);
        // Pull-down refresh listener
        swipeContainer.setOnRefreshListener(() -> submissionsViewModel.invalidateDataSource());

        // RecyclerView
        RecyclerView mRecyclerView = view.findViewById(R.id.my_recycler_view);
        // Changes in content does not change layout size
        mRecyclerView.setHasFixedSize(true);
        // LinearLayout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Hide FAB on scroll
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        // TODO: Hide submission
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        // Adapter
        mAdapter = new SubmissionsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        // Currently scoped to the current Fragment
        submissionsViewModel = ViewModelProviders.of(this).get(SubmissionsViewModel.class);
        // Switches to Userless mode, and then populate PagedList when completed
        submissionsViewModel.getRedditClient().observe(this, redditClient -> {
            this.redditClient = redditClient;
            // Observer Pattern: Will be used to observe changes to the PagedList<Submissions>
            // Initially null, so loads submissions and enacts observer
            // Submit new set of data and set refreshing false
            submissionsViewModel.getSubmissions(redditClient).observe(SubmissionsFragment.this, submissions -> {
                // TODO: Changing listing sometimes puts you in the middle (possibly due to DiffUtil)
                mAdapter.submitList(submissions);
                swipeContainer.setRefreshing(false);
            });
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
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.best:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.BEST));
                break;
            case R.id.hot:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.HOT));
                break;
            case R.id.sort_new:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.NEW));
                break;
            case R.id.controversial_hour:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.HOUR));
                break;
            case R.id.controversial_day:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.DAY));
                break;
            case R.id.controversial_week:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.WEEK));
                break;
            case R.id.controversial_month:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.MONTH));
                break;
            case R.id.controversial_year:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.YEAR));
                break;
            case R.id.controversial_all_time:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.CONTROVERSIAL).timePeriod(TimePeriod.ALL));
                break;
            case R.id.top_hour:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.HOUR));
                break;
            case R.id.top_day:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.DAY));
                break;
            case R.id.top_week:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.WEEK));
                break;
            case R.id.top_month:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.MONTH));
                break;
            case R.id.top_year:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.YEAR));
                break;
            case R.id.top_all_time:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.TOP).timePeriod(TimePeriod.ALL));
                break;
            case R.id.rising:
                swipeContainer.setRefreshing(true);
                submissionsViewModel.changeDataSource(redditClient.frontPage().sorting(SubredditSort.RISING));
                break;
            case R.id.change_view:
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
