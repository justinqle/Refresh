package com.justinqle.refresh.architecture;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.NavigationMenuView;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.justinqle.refresh.AccountLogin;
import com.justinqle.refresh.ExpandCollapseAnimations;
import com.justinqle.refresh.R;
import com.justinqle.refresh.retrofit.NetworkService;

import org.json.JSONObject;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static Context contextOfApplication;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PostViewModel postViewModel;
    private static SwipeRefreshLayout swipeContainer;

    private SharedPreferences sharedPreferences;
    private boolean loggedIn;
    private LinearLayout dropdown;

    private static final int ADD_ACCOUNT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d(TAG, "Access Token: " + sharedPreferences.getString("access_token", null));
        Log.d(TAG, "Refresh Token: " + sharedPreferences.getString("refresh_token", null));
        Log.d(TAG, "Logged In: " + sharedPreferences.getBoolean("logged_in", false));

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);

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
        disableNavigationViewScrollbars(navigationView);

        // Setting up dropdown menu for adding Accounts in Nav Header
        View headerView = navigationView.getHeaderView(0);
        ConstraintLayout header = headerView.findViewById(R.id.nav_header_layout);
        dropdown = headerView.findViewById(R.id.account_dropdown);
        ImageView expandArrow = header.findViewById(R.id.header_icon);
        header.setOnClickListener((view) -> {
            if (dropdown.getVisibility() == View.GONE) {
                ExpandCollapseAnimations.expand(dropdown);
                expandArrow.animate().rotation(180).setDuration(250).start();
            } else {
                ExpandCollapseAnimations.collapse(dropdown);
                expandArrow.animate().rotation(0).setDuration(250).start();
            }
        });
        // Set title of header and add header submenu items based on logged in/out status and different account options
        loggedIn = sharedPreferences.getBoolean("logged_in", false);
        TextView headerTitle = header.findViewById(R.id.header_title);
        // "Guest" if logged out
        if (!loggedIn) {
            headerTitle.setText(R.string.guest_user);
        }
        // Request the user's handle to put on header
        else {
            NetworkService.getInstance().getJSONApi().getUser().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
        // Always an add account menu option
        addHeaderMenuItem(R.id.add_account, getString(R.string.add_account), getDrawable(R.drawable.add_account_light)).setOnClickListener(v -> {
            startActivityForResult(new Intent(this, AccountLogin.class), ADD_ACCOUNT_REQUEST);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        });

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

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        // invalidate to loadInitial() again
        swipeContainer.setOnRefreshListener(() -> {
            swipeContainer.setRefreshing(true);
            // invalidate data source to force refresh
            postViewModel.invalidateDataSource();
        });

        // submit new set of data and set refreshing false
        postViewModel.getPosts().observe(this, posts -> {
            mAdapter.submitList(posts);
        });
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    public static void loading(boolean show) {
        swipeContainer.setRefreshing(show);
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
            case R.id.profile:

            case R.id.settings:

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Returned request code: " + requestCode);
        Log.d(TAG, "Returned result code: " + resultCode);
        if (requestCode == ADD_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Login succeeded");
                finish();
                startActivity(getIntent());
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Login failed");
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * Adds item to header menu while also returning the view to set click event
     *
     * @param id
     * @param text
     * @param icon
     * @return
     */
    private View addHeaderMenuItem(int id, String text, Drawable icon) {
        View menuItem = LayoutInflater.from(this).inflate(R.layout.account_menu_item, dropdown, false);

        menuItem.setId(id);
        TextView textView = menuItem.findViewById(R.id.account_text);
        textView.setText(text);
        ImageButton imageButton = menuItem.findViewById(R.id.account_icon);
        imageButton.setImageDrawable(icon);

        dropdown.addView(menuItem);
        return menuItem;
    }
}
