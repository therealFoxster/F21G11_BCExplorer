package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bcexplorer.database.AppDatabase;
import com.example.bcexplorer.global.BottomNavigationViewPagerAdapter;
import com.example.bcexplorer.global.LocationFragment;
import com.example.bcexplorer.home.HomeFragment;
import com.example.bcexplorer.info.CreditsFragment;
import com.example.bcexplorer.info.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 bottomNavigationViewPager;
    ActionBar actionBar;
    FragmentManager fragmentManager;

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

                    // Show back button if there are items in the backstack and locationFragment is visible
                    if (fragmentManager.getBackStackEntryCount() > 0 && locationFragment != null) {
                        actionBar.setTitle(R.string.location);
                        showBackButton();
                    } else if (locationFragment == null) { // Location fragment not visible (home fragment is visible
                        actionBar.setTitle(R.string.home);
                        hideBackButton();
                    }

                    bottomNavigationViewPager.setCurrentItem(0, false);

//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();
                    break;
                case R.id.bottom_nav_saved:
                    actionBar.setTitle(R.string.saved);
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
            if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
                switch (bottomNavigationViewPager.getCurrentItem()) {
                    case 0:
                        actionBar.setTitle("Home");
                        break;
                    case 1:
                        actionBar.setTitle("Location");
                        break;
                    case 2:
                        actionBar.setTitle("Information");
                        break;
                }
                hideBackButton();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        MenuItem item = menu.findItem(R.id.location_save);
        item.setVisible(false);
        return true;
    }

    // Method that handles actionBar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
//                getSupportFragmentManager().popBackStack();
//                getSupportFragmentManager().popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LocationFragment locationFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag("LOCATION_FRAGMENT");
                CreditsFragment creditsFragment = (CreditsFragment) getSupportFragmentManager().findFragmentByTag("CREDITS_FRAGMENT");

                // Pop backstack until encounter correct backstack entry
                if (locationFragment != null)
                    getSupportFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (creditsFragment != null)
                    getSupportFragmentManager().popBackStack("info", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

}