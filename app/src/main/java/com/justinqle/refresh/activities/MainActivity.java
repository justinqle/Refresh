package com.justinqle.refresh.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.justinqle.refresh.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());

        // Drawer (Container)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Widget that can be used inside Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Listener to swap between Nav Menu and Account Switcher
        View navHeader = navigationView.getHeaderView(0);
        navHeader.setOnClickListener(view -> {
            ImageView arrow = navHeader.findViewById(R.id.header_icon);
            if (!view.isActivated()) {
                view.setActivated(true);
                arrow.animate().rotation(180).setDuration(200).start();
                navigationView.getMenu().setGroupVisible(R.id.navigation, false);
                navigationView.getMenu().setGroupVisible(R.id.account_switcher, true);
            } else {
                view.setActivated(false);
                arrow.animate().rotation(0).setDuration(200).start();
                navigationView.getMenu().setGroupVisible(R.id.account_switcher, false);
                navigationView.getMenu().setGroupVisible(R.id.navigation, true);
            }
        });

        // Navigation Component
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Listener for every navigation change
        navController.addOnNavigatedListener((controller, destination) -> {
            // Since scrolling hides toolbar, ensures navigation changes reshows app bar
            ((AppBarLayout) findViewById(R.id.app_bar)).setExpanded(true, true);
            // Hides floating action button on fragments that don't utilize it
            if (destination.getId() != R.id.postsFragment) {
                fab.hide();
            }
        });
        // Sets up Toolbar with Navigation Component
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);
        NavigationUI.setupWithNavController(toolbar, navController, drawer);
        // Sets up Navigation View with Navigation Component
        NavigationUI.setupWithNavController(navigationView, navController);
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
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }
}
