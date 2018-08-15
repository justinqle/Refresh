package com.justinqle.refresh.activities;

import android.content.Intent;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.justinqle.refresh.R;
import com.justinqle.refresh.animations.ExpandCollapse;
import com.justinqle.refresh.models.listing.Child;
import com.justinqle.refresh.models.listing.Subreddit;
import com.justinqle.refresh.models.user.User;
import com.justinqle.refresh.networking.NetworkService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int ADD_ACCOUNT_REQUEST = 1;

    // Toolbar
    private Toolbar toolbar;
    private TextView currentSubreddit;
    private TextView currentSort;

    // Nav Header
    private LinearLayout dropdown;
    private SubMenu subMenu;
    private List<Child> subredditMenuItems = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        currentSubreddit = findViewById(R.id.current_subreddit);
        currentSort = findViewById(R.id.current_sort);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

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
        boolean loggedIn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("logged_in", false);
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

        // Set Account group and Saved listing to visible in NavigationView when logged in
        if (loggedIn) {
            navigationView.getMenu().setGroupVisible(R.id.account, true);
            navigationView.getMenu().findItem(R.id.saved).setVisible(true);
        }
        // Set default, checked item
        navigationView.setCheckedItem(R.id.frontpage);

        // TODO Add Pinned/All subreddits to NavigationView submenu, Settings option for showing all subscriptions in nav menu
//        Menu menu = navigationView.getMenu();
//        MenuItem menuItem = menu.getItem(menu.size() - 1);
//        subMenu = menuItem.getSubMenu();
//        String subredditsType = loggedIn ? "mine/subscriber" : "default";
//        NetworkService.getInstance().getJSONApi().getSubreddits(subredditsType, 100, null).enqueue(new Callback<Listing>() {
//            @Override
//            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
//                Listing listing = response.body();
//                if (listing != null) {
//                    List<Child> children = listing.getData().getChildren();
//                    subredditMenuItems.addAll(children);
//                    // More items? Page using key
//                    if (listing.getData().getAfter() != null) {
//                        NetworkService.getInstance().getJSONApi().getSubreddits(subredditsType, 100, listing.getData().getAfter()).enqueue(this);
//                    } else {
//                        addSubredditMenuItemsCallback();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
//                t.printStackTrace();
//            }
//        });
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentSubreddit.getText().equals(getString(R.string.frontpage))) {
            menu.findItem(R.id.sort).getSubMenu().findItem(R.id.best).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

//    private void changeListing(String subreddit, String sort, String time) {
//        // Refreshes until network request is finished
//        swipeContainer.setRefreshing(true);
//        // Updates Toolbar
//        currentSubreddit.setText(subreddit);
//        String sortAndTime = sort;
//        // If time is not null, append to time to sortAndTime string
//        if (time != null) {
//            sortAndTime += " " + getString(R.string.bullet) + " " + time;
//            // "All Time" string should be "all" for query
//            if (time.equals(getString(R.string.all_time))) {
//                time = "all";
//            }
//            time = time.toLowerCase();
//        }
//        currentSort.setText(sortAndTime);
//        // Special Cases
//        // If Frontpage, get Frontpage listing (not /r/FrontPage listing)
//        if (subreddit.equals(getString(R.string.frontpage))) {
//            // Not a subreddit, or null
//            subreddit = null;
//        }
//        // Remove old Observers
//        postViewModel.getPosts().removeObservers(this);
//        // Updates RecyclerView Listing
//        postViewModel.getNewPosts(subreddit, sort.toLowerCase(), time).observe(this, posts -> {
//            mAdapter.submitList(posts);
//            swipeContainer.setRefreshing(false);
//            // Expand toolbar
//            ((AppBarLayout) toolbar.getParent()).setExpanded(true, true);
//        });
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        boolean isToggle = false;
        int id = item.getItemId();

        // If item is already checked, do nothing
        if (!item.isChecked()) {
            // If item is apart of subreddits group submenu
            if (item.getGroupId() == R.id.subreddits) {
                String subreddit = item.getTitle().toString();
                // Default sort of Subreddits is "Hot"
                String sort = getString(R.string.hot);
                //changeListing(subreddit, sort, null);
                // Calls onPrepareOptionsMenu()
                invalidateOptionsMenu();
            }
            // Apart of regular menu
            else {
                switch (id) {
                    case R.id.frontpage:
                        //changeListing(getString(R.string.frontpage), getString(R.string.best), null);
                        break;
                    case R.id.popular:
                        //changeListing(getString(R.string.popular), getString(R.string.hot), null);
                        break;
                    case R.id.all:
                        //changeListing(getString(R.string.all), getString(R.string.hot), null);
                        break;
                    case R.id.saved:
                        break;
                    case R.id.profile:
                        break;
                    case R.id.inbox:
                        break;
                    case R.id.dark_mode:
                        isToggle = true;
                        ((Switch) item.getActionView()).toggle();
                        break;
                    case R.id.settings:
                        break;
                }
            }
        }

        if (!isToggle) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
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
