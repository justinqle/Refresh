package com.justinqle.refresh.activities;

import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.justinqle.refresh.R;
import com.justinqle.refresh.fragments.BottomNavigationDrawerFragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BottomAppBar
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        // Fragments can create custom options menu with setHasOptionsMenu()
        setSupportActionBar(bottomAppBar);
        // BottomNavigationDrawerFragment
        BottomNavigationDrawerFragment bottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
        // Clicking on BottomAppBar Navigation Icon shows BottomNavigationDrawerFragment
        bottomAppBar.setNavigationOnClickListener(view -> bottomNavigationDrawerFragment.show(getSupportFragmentManager(), bottomNavigationDrawerFragment.getTag()));

        // Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab);
        // TODO: Snackbars cover bottom app bar
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());

        // Navigation Component
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // Listener for every navigation change to hide/show/modify overarching UI elements
        navController.addOnNavigatedListener((controller, destination) -> {
            // Closes BottomNavigationDrawer when Navigating
            // TODO: There isn't a built-in way to close it on menu selection???
            if (bottomNavigationDrawerFragment.isVisible()) {
                bottomNavigationDrawerFragment.dismiss();
            }

            // Since scrolling hides app bar, ensures navigation changes reshows app bar
            ((AppBarLayout) findViewById(R.id.app_bar)).setExpanded(true, true);

            // FAB alignment
            if (destination.getId() == R.id.postsFragment) {
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            } else if (destination.getId() == R.id.commentsFragment) {
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            }

            // Hides bottom app bar & floating action button on fragments that don't utilize it
            if (destination.getId() == R.id.loginFragment) {
                getSupportActionBar().hide();
                fab.hide();
            }
            // Makes sure bottom app bar & floating action button gets shown again if hidden before
            else {
                getSupportActionBar().show();
                fab.show();
            }
        });

        // Sets up toolbar with Navigation Component
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController);
    }

    // Closes sidebar on back press
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

}
