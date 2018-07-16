package com.justinqle.refresh.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.justinqle.refresh.R;
import com.justinqle.refresh.animations.ExpandCollapse;
import com.justinqle.refresh.architecture.PostAdapter;
import com.justinqle.refresh.architecture.PostViewModel;
import com.justinqle.refresh.models.listing.Child;
import com.justinqle.refresh.models.listing.Listing;
import com.justinqle.refresh.models.listing.Subreddit;
import com.justinqle.refresh.models.user.User;
import com.justinqle.refresh.networking.NetworkService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int ADD_ACCOUNT_REQUEST = 1;

    private PostAdapter mAdapter;
    private PostViewModel postViewModel;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayout dropdown;
    private SubMenu subMenu;
    private List<Child> subredditMenuItems = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // TODO Different access tokens are retrieved twice on Authentication, due to data race between thread retrieving posts and thread retrieving subreddit items for nav menu
        Log.d(TAG, "Access Token: " + sharedPreferences.getString("access_token", null));
        Log.d(TAG, "Refresh Token: " + sharedPreferences.getString("refresh_token", null));
        Log.d(TAG, "Logged In: " + sharedPreferences.getBoolean("logged_in", false));

        // Swipe Container
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setRefreshing(true);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());

        // Drawer (Container)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Widget that can be used inside Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);

        // Setting up dropdown menu for adding Accounts in Nav Header
        View headerView = navigationView.getHeaderView(0);
        ConstraintLayout header = headerView.findViewById(R.id.nav_header_layout);
        dropdown = headerView.findViewById(R.id.account_dropdown);
        ImageView expandArrow = header.findViewById(R.id.header_icon);
        header.setOnClickListener((view) -> {
            if (dropdown.getVisibility() == View.GONE) {
                ExpandCollapse.expand(dropdown);
                expandArrow.animate().rotation(180).setDuration(200).start();
            } else {
                ExpandCollapse.collapse(dropdown);
                expandArrow.animate().rotation(0).setDuration(200).start();
            }
        });

        // TODO: Multi-account functionality
        // Set title of header and add header submenu items based on logged in/out status and different account options
        boolean loggedIn = sharedPreferences.getBoolean("logged_in", false);
        TextView headerTitle = header.findViewById(R.id.header_title);
        // "Guest" if logged out
        if (!loggedIn) {
            headerTitle.setText(R.string.log_in);
            addHeaderMenuItem(R.id.add_account, getString(R.string.add_account), getDrawable(R.drawable.add_account_light)).setOnClickListener(v -> {
                startActivityForResult(new Intent(this, AccountLogin.class), ADD_ACCOUNT_REQUEST);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            });
        }
        // Request the user's handle to put on header
        else {
            NetworkService.getInstance().getJSONApi().getUser().enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    User user = response.body();
                    if (user != null) {
                        headerTitle.setText(user.getName());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
            addHeaderMenuItem(R.id.log_out, getString(R.string.log_out), getDrawable(R.drawable.logout)).setOnClickListener(v -> {
                Log.d(TAG, "Logout");
                // TODO: Revoke access and refresh token
                PreferenceManager.getDefaultSharedPreferences(this).edit().remove("logged_in").remove("access_token").remove("refresh_token").apply();
                finish();
                startActivity(getIntent());
                Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
            });
        }

        // Add User-specific menu items to NavigationView when logged in
        if (loggedIn) {
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
        }

        // Add Subreddits to NavigationView submenu (default for logged out, user-specific for logged in)
        // TODO: Clicking on Navigation SubMenu items initially isn't smooth
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(menu.size() - 1);
        subMenu = menuItem.getSubMenu();
        String subredditsType = loggedIn ? "mine/subscriber" : "default";
        NetworkService.getInstance().getJSONApi().getSubreddits(subredditsType, 100, null).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                Listing listing = response.body();
                if (listing != null) {
                    List<Child> children = listing.getData().getChildren();
                    subredditMenuItems.addAll(children);
                    // More items? Page using key
                    if (listing.getData().getAfter() != null) {
                        NetworkService.getInstance().getJSONApi().getSubreddits(subredditsType, 100, listing.getData().getAfter()).enqueue(this);
                    } else {
                        addSubredditMenuItemsCallback();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
        // Set the default submenu item ("frontpage") to be selected
        navigationView.setCheckedItem(R.id.default_page);

        // RecyclerView
        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        // Changes in content does not change layout size
        mRecyclerView.setHasFixedSize(true);
        // LinearLayout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initial animation / refresh
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());

        // Hide FAB on scroll
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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
            swipeContainer.setRefreshing(true);
            // invalidate data source to force refresh
            postViewModel.invalidateDataSource();
        });

        // submit new set of data and set refreshing false
        postViewModel.getPosts().observe(this, posts -> {
            // TODO Immediately sets refreshing false for some reason
            swipeContainer.setRefreshing(false);
            mAdapter.submitList(posts);
        });
    }

    private void addSubredditMenuItemsCallback() {
        // TODO Could improve GSON deserialization structure to prevent incessant casting and utilize a more logical compareTo method within the class itself
        Collections.sort(subredditMenuItems, (o1, o2) -> ((Subreddit) o1.getData()).getDisplayName().compareToIgnoreCase(((Subreddit) o2.getData()).getDisplayName()));
        for (Child child : subredditMenuItems) {
            Log.d(TAG, ((Subreddit) child.getData()).getDisplayName());
            // TODO User can interact with UI and see that menu items haven't been added quite yet
            subMenu.add(R.id.subreddits, Menu.NONE, Menu.NONE, ((Subreddit) child.getData()).getDisplayName()).setCheckable(true);
        }
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overflow, menu);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        // If item is already checked, do nothing
        if (!item.isChecked()) {
            // If item is apart of subreddits group submenu
            if (item.getGroupId() == R.id.subreddits) {
                String string = item.getTitle().toString();
                //postViewModel.invalidateDataSource();
            }
            // Apart of regular menu
            else {
                int id = item.getItemId();
                switch (id) {
                    case R.id.profile:

                    case R.id.dark_mode:

                    case R.id.settings:

                    default:
                        break;
                }
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Login failed");
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Adds item to header menu while also returning the view to set click event
     *
     * @param id   ID of menu item
     * @param text Text of menu item
     * @param icon Corresponding icon of menu item
     * @return The inflated view
     */
    private View addHeaderMenuItem(int id, String text, Drawable icon) {
        View menuItem = LayoutInflater.from(this).inflate(R.layout.account_menu_item, dropdown, false);

        menuItem.setId(id);
        TextView textView = menuItem.findViewById(R.id.account_text);
        textView.setText(text);
        ImageView imageView = menuItem.findViewById(R.id.account_icon);
        imageView.setImageDrawable(icon);

        dropdown.addView(menuItem);
        return menuItem;
    }
}
