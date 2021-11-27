package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityExploreBinding;
import com.example.bcexplorer.global.BottomNavigationViewPagerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityExploreBinding b;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    List<ExploreItem> ExploreList = new ArrayList<>();
    TextView txtViewExploreTitle;
    TextView txtViewTransit;
    String LIST_TYPE;
    int idx;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        AddData();
        txtViewExploreTitle = findViewById(R.id.txtViewExploreTitle);
        txtViewTransit = findViewById(R.id.txtViewTransit);

        //google map fragment
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)){
            idx = 0;
        } else if (LIST_TYPE.equals(Constants.VANCOUVER)){
            idx = 1;
        } else if (LIST_TYPE.equals(Constants.WHISTLER)){
            idx = 2;
        }

        txtViewExploreTitle.setText("Getting Around " + ExploreList.get(idx).getName());
        //TODO: need to change
        txtViewTransit.setText("Dummy text");
        location = new LatLng(ExploreList.get(idx).getLatitude(), ExploreList.get(idx).getLongitude());

    }


    // setting GoogleMap marker
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(ExploreList.get(idx).getName());
        markerOptions.snippet("British Columbia");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
    }

    //Add data
    private void AddData() {
        ExploreList.add(new ExploreItem("White Rock", 49.02057428391075,-122.8003379497681,
                "From The Airport" ));
        ExploreList.add(new ExploreItem("DT Vancouver",49.28261554728643,-123.11546222521058,
                "From the Airport"));
        ExploreList.add(new ExploreItem("Whistler", 50.12916060058702,-122.9507097172427,
                "From the Airport"));
    }

}