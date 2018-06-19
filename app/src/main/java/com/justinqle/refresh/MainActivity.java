package com.justinqle.refresh;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.paging.PostAdapter;
import com.justinqle.refresh.paging.PostDataSourceFactory;
import com.justinqle.refresh.retrofit.NetworkService;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static Context contextOfApplication;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO Encapsulate data in ViewModels
    private LiveData<PagedList<Post>> posts;
    // TODO Should be in the ViewModel but shown here for simplicity
    private PostDataSourceFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Drawer (Container)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Widget that can be used inside Drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // Changes in content does not change layout size
        mRecyclerView.setHasFixedSize(true);
        // LinearLayout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initial animation / refresh
        SlideInUpAnimator slideInUpAnimator = new SlideInUpAnimator();
        slideInUpAnimator.setRemoveDuration(250);
        slideInUpAnimator.setAddDuration(250);
        slideInUpAnimator.setInterpolator(null);
        mRecyclerView.setItemAnimator(slideInUpAnimator);

        // Adapter
        mAdapter = new PostAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // initial page size to fetch can also be configured here too
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(20).build();
        factory = new PostDataSourceFactory(NetworkService.getInstance().getJSONApi());
        posts = new LivePagedListBuilder(factory, config).build();
        posts.observe(this, new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(@Nullable PagedList<Post> tweets) {
                mAdapter.submitList(tweets);
            }
        });

        // invalidate to loadInitial() again
        SwipeRefreshLayout swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                factory.postLiveData.getValue().invalidate();
            }
        });
        // submit new set of data and set refreshing false
        posts.observe(this, new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(@Nullable PagedList<Post> posts) {
                mAdapter.submitList(posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.add_account:
                startActivity(new Intent(this, AccountLogin.class));
            case R.id.nav_camera:

            case R.id.nav_gallery:

            case R.id.nav_slideshow:

            case R.id.nav_manage:

            case R.id.nav_share:

            case R.id.nav_send:

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
