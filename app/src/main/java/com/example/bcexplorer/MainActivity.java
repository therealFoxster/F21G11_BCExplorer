package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bcexplorer.database.AppDatabase;
import com.example.bcexplorer.database.Location;
import com.example.bcexplorer.global.BottomNavigationViewPagerAdapter;
import com.example.bcexplorer.global.LocationFragment;
import com.example.bcexplorer.home.HomeFragment;
import com.example.bcexplorer.info.CreditsFragment;
import com.example.bcexplorer.info.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    public static ViewPager2 bottomNavigationViewPager;
    ActionBar actionBar;
    public static FragmentManager fragmentManager;

    public static AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        fragmentManager = getSupportFragmentManager();

        setupDatabase();
        setupViewPager();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((@NonNull MenuItem item) -> {
            hideBackButton(); // Hides back button everytime a bottom navigation item is selected

            if (fragmentManager.getBackStackEntryCount() > 1) {
                fragmentManager.popBackStack();
            }

            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag("LOCATION_FRAGMENT");
                    Fragment locationListFragment = fragmentManager.findFragmentByTag("LOCATION_LIST_FRAGMENT");

                    // Show back button if there are items in the backstack and locationFragment is visible
                    if (fragmentManager.getBackStackEntryCount() > 0 && locationFragment != null) {
                        actionBar.setTitle(R.string.location);
                        showBackButton();
                    } else if (fragmentManager.getBackStackEntryCount() > 0 && locationListFragment != null) {
                        actionBar.setTitle(R.string.locations);
                        showBackButton();
                    }
                    else if (locationFragment == null) { // Location fragment not visible (home fragment is visible
                        actionBar.setTitle(R.string.home);
                        hideBackButton();
                    }

                    bottomNavigationViewPager.setCurrentItem(0, false);

//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();
                    break;
                case R.id.bottom_nav_saved:
                    LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");
                    // If there are items in the backstack and location view (saved) is visible
                    if (fragmentManager.getBackStackEntryCount() > 0 && savedLocationFragment != null) {
                        actionBar.setTitle(R.string.location);
                        showBackButton();
                    } else {
                        actionBar.setTitle(R.string.saved);
                        hideBackButton();
                    }

                    bottomNavigationViewPager.setCurrentItem(1, false);

//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();
                    break;
                case R.id.bottom_nav_info:
                    CreditsFragment creditsFragment = (CreditsFragment) fragmentManager.findFragmentByTag("CREDITS_FRAGMENT");
                    // If there are items in the backstack and credits fragment is visible
                    if (fragmentManager.getBackStackEntryCount() > 0 && creditsFragment != null) {
                        actionBar.setTitle(R.string.credits);
                        showBackButton();
                    } else if (creditsFragment == null) { // Credits fragment is not visible
                        actionBar.setTitle(R.string.info);
                        hideBackButton();
                    }

                    bottomNavigationViewPager.setCurrentItem(2, false);

//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new InfoFragment()).commit();
                    break;
            }

            return true;
        });

        // Show home page at start
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);

        fragmentManager.addOnBackStackChangedListener(() -> {
            // Hides back button if at home fragment
                switch (bottomNavigationViewPager.getCurrentItem()) {
                    case 0:
                        Fragment locationFragment = fragmentManager.findFragmentByTag("LOCATION_FRAGMENT");
                        Fragment locationListFragment = fragmentManager.findFragmentByTag("LOCATION_LIST_FRAGMENT");
                        if (locationFragment == null && locationListFragment == null) { // Location fragment and location list fragment are not visible
                            actionBar.setTitle(R.string.home);
                            hideBackButton();
                        } else if (locationFragment != null)
                            actionBar.setTitle(R.string.location);

                        break;
                    case 1:
                        Fragment savedLocationFragment = fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");
                        if (savedLocationFragment == null) {
                            actionBar.setTitle(R.string.saved);
                            hideBackButton();
                        } else if (savedLocationFragment.isVisible())
                            actionBar.setTitle(R.string.location);
                        break;
                    case 2:
                        Fragment creditsFragment = fragmentManager.findFragmentByTag("CREDITS_FRAGMENT");
                        if (creditsFragment == null) {
                            actionBar.setTitle(R.string.info);
                            hideBackButton();
                        } else if (creditsFragment.isVisible())
                            actionBar.setTitle(R.string.credits);
                        break;
                }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag("LOCATION_FRAGMENT");
        LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");

        getMenuInflater().inflate(R.menu.menu_location_view, menu);

        MenuItem item = menu.findItem(R.id.location_save);
        if (locationFragment == null && savedLocationFragment == null)
            item.setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag("LOCATION_FRAGMENT");
        LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");

        if (menu != null && menu.findItem(R.id.location_save) != null && locationFragment == null && savedLocationFragment == null)
            menu.findItem(R.id.location_save).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    // Method that handles actionBar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home: // Back button
//                getSupportFragmentManager().popBackStack();
//                getSupportFragmentManager().popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LocationFragment locationFragment = (LocationFragment) fragmentManager.findFragmentByTag("LOCATION_FRAGMENT");
                CreditsFragment creditsFragment = (CreditsFragment) fragmentManager.findFragmentByTag("CREDITS_FRAGMENT");
                LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");
                Fragment locationListFragment = fragmentManager.findFragmentByTag("LOCATION_LIST_FRAGMENT");

                // Pop backstack until encounter correct backstack entry
                if (locationFragment != null)
                    getSupportFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (creditsFragment != null)
                    getSupportFragmentManager().popBackStack("info", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (savedLocationFragment != null)
                    getSupportFragmentManager().popBackStack("saved", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (locationListFragment != null)
                    getSupportFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        bottomNavigationViewPager = findViewById(R.id.bottomNavigationViewPager);
        BottomNavigationViewPagerAdapter bottomNavigationViewPagerAdapter = new BottomNavigationViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        Fragment homeFragment = new HomeFragment();

        Fragment savedFragment = new SavedFragment();

        Fragment infoFragment = new InfoFragment();

        bottomNavigationViewPagerAdapter.addFragment(homeFragment);
        bottomNavigationViewPagerAdapter.addFragment(savedFragment);
        bottomNavigationViewPagerAdapter.addFragment(infoFragment);

        bottomNavigationViewPager.setAdapter(bottomNavigationViewPagerAdapter);
        bottomNavigationViewPager.setUserInputEnabled(false);
    }

    private void setupDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "BCExplorer.db").createFromAsset("database/BCExplorer_init.db").build();

        // Making sure the database is properly created from init database file
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            database.locationDAO().getAllLocations();
        });
    }

    private void hideBackButton() {
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void showBackButton() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public static void refreshSavedPage() {
        // Detaching and reattaching the saved page to refresh its data
            Fragment savedPage = ((BottomNavigationViewPagerAdapter) bottomNavigationViewPager.getAdapter()).getItem(1);
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.detach(savedPage);
//            fragmentTransaction.attach(savedPage);
//            fragmentTransaction.commit();

        // Calling onResume() to refresh saved page
            savedPage.onResume(); savedPage.onResume(); savedPage.onResume();

        // Manually switching bottom navigation pages to invoke onResume() to refresh saved page; not the most optimal method but it's the most reliable in my testing
//        int selectedItem = bottomNavigationView.getSelectedItemId();
//        int ms = 50;
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
////                Log.d("LOCATION_FRAGMENT", "Switching to info");
//                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_info);
//            }
//        }, ms);
////                new Handler().postDelayed(new Runnable() {
////                    public void run() {
////                        Log.d("LOCATION_FRAGMENT", "Switching to saved");
////                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.bottom_nav_saved);
////                    }
////                }, ms + 50);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
////                Log.d("LOCATION_FRAGMENT", "Switching to original");
//                bottomNavigationView.setSelectedItemId(selectedItem);
//            }
//        }, ms + 50);
    }

    public static void refreshLocationListPage() {
        Fragment locationListFragment = fragmentManager.findFragmentByTag("LOCATION_LIST_FRAGMENT");
        if (locationListFragment != null) {
            locationListFragment.onResume();
            locationListFragment.onResume();
            locationListFragment.onResume();
        }
    }

}