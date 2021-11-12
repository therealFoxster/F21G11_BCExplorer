package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bcexplorer.home.HomeFragment;
import com.example.bcexplorer.info.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 bottomNavigationViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        setupViewPager();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener((@NonNull MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    actionBar.setTitle("Home");
//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new HomeFragment()).commit();
                    bottomNavigationViewPager.setCurrentItem(0, false);
                    break;
                case R.id.bottom_nav_saved:
                    actionBar.setTitle("Saved");
                    // TODO: Start fragment/activity from here
//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new BlankFragment()).commit();
                    bottomNavigationViewPager.setCurrentItem(1, false);

                    break;
                case R.id.bottom_nav_info:
                    actionBar.setTitle("Information");
//                    getSupportFragmentManager().beginTransaction().replace(R.id.constraintLayoutContainer, new InfoFragment()).commit();
                    bottomNavigationViewPager.setCurrentItem(2, false);
                    break;
            }

            return true;
        });

        // Show home page at start
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }

    private void setupViewPager() {
        bottomNavigationViewPager = findViewById(R.id.bottomNavigationViewPager);
        BottomNavigationViewPagerAdapter bottomNavigationViewPagerAdapter = new BottomNavigationViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        Fragment homeFragment = new HomeFragment();
        Fragment savedFragment = new BlankFragment();
        Fragment infoFragment = new InfoFragment();

        bottomNavigationViewPagerAdapter.addFragment(homeFragment);
        bottomNavigationViewPagerAdapter.addFragment(savedFragment);
        bottomNavigationViewPagerAdapter.addFragment(infoFragment);

        bottomNavigationViewPager.setAdapter(bottomNavigationViewPagerAdapter);
        bottomNavigationViewPager.setUserInputEnabled(false);
    }
}