package com.justinqle.refresh.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinqle.refresh.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);

        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        // Sets up Navigation View with Navigation Component
        NavigationUI.setupWithNavController(navigationView, navController);

        return view;
    }

}
