package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityReadMoreBinding;
import com.example.bcexplorer.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ReadMoreActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityReadMoreBinding b;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    String LIST_TYPE = Constants.VANCOUVER;
    String GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;
    String locationName;
    LatLng locationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityReadMoreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around White Rock");
            b.airportTxt.setText("The nearest airport to White Rock is Bellingham (BLI). However, there are better options for getting to White Rock. TransLink CA operates a bus from Bridgeport Station @ Bay 9 to White Rock Centre @ Bay 10 every 15 minutes. Tickets cost $3 and the journey takes 45 min");
            b.taxisTxt.setText("Uber and Lyft are a great option but sometimes are saturated. Local taxi services can be the next best choice");
            b.bikesTxt.setText("One of the best ways to explore White Rock and the Semiahmoo Peninsula is by bike. Believe it or not, it IS possible to go for a great bike ride in White Rock and avoid climbing any major hills!");
            b.walkTxt.setText("Whether you're getting ready to hike, bike, trail run, or explore other outdoor activities, AllTrails has 12 scenic trails in the White Rock area");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHITE_ROCK;
            locationName = "White Rock, BC, Canada";
        }

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around DT Vancouver");

            b.airportTxt.setText("With a short 30 min commute from the airport by public transport or car, the location its a very good location that won't burn your pockets to get to");
            b.taxisTxt.setText("Taxi operators can accommodate wheelchairs and mobility aids. Below is a list of taxi operators who are licensed to pick up passengers from YVR. Taxis serving the South Terminal include Richmond Taxi, Garden City Cabs and Kimber Cabs.");
            b.bikesTxt.setText("Bike lanes in the downtown core make cycling an easy way to move around within minutes and choose different destinations within the area");
            b.walkTxt.setText("An amazing city to walk around, with stunning views of the vast Pacific Ocean on one side and the towering North Shore mountains on the other");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;
            locationName = "Downtown Vancouver, BC, Canada";
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around Whistler");
            b.airportTxt.setText("The nearest airport its the YVR airport. Several shuttles run from the airport to Whistler Village which is the most important part of the town. It's a 2 hour drive");
            b.taxisTxt.setText("Take advantage of free transit shuttle services connecting ski lifts and popular parks (seasonal)");
            b.bikesTxt.setText("Use the car-free Valley Trail network and the public transit services to explore lakes, parks and other neighbourhoods");
            b.walkTxt.setText("The pedestrian-only Village Stroll allows easy walking access between shops, restaurants, ski lifts and accommodation");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHISTLER;
            locationName = "Whistler, BC, Cananda";
        }

        locationLatLng = getLocationFromAddress(this, locationName);
        initGoogleMap(savedInstanceState);
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        b.mapView1.onCreate(mapViewBundle);

        Handler handler = new Handler(getMainLooper());
        handler.post(() -> {
            b.mapView1.getMapAsync(this);
        });
    }

    boolean isSaved = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        MenuItem item = menu.findItem(R.id.location_save);

        if(Utils.getBoolean(this, GETTING_AROUND_LIST_TYPE))
        {
            item.setIcon(R.drawable.ic_location_unsave);
        }
        else
        {
            item.setIcon(R.drawable.ic_location_save);
        }

        item.setVisible(true);
        return true;
    }

    // Method that handles actionBar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) { // Back button
            finish();
            overridePendingTransition(R.anim.none, R.anim.exit_rev);
        }
        else if(id == R.id.location_save)
        {
            // If save icon is visible (location is not saved)
            if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_save).getConstantState()) {
                item.setIcon(R.drawable.ic_location_unsave); // Change icon to unsave

                // Set saved status in database
                Utils.store(this, GETTING_AROUND_LIST_TYPE, true);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            }
            // If unsave icon is visible (location is already saved)
            else if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_unsave).getConstantState()) {
                item.setIcon(R.drawable.ic_location_save); // Change icon to save

                // Set unsaved status in database

                Utils.store(this, GETTING_AROUND_LIST_TYPE, false);
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        b.mapView1.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        b.mapView1.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        b.mapView1.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        b.mapView1.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(locationLatLng).title(locationName));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));

    }

    @Override
    public void onPause() {
        b.mapView1.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        b.mapView1.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        b.mapView1.onLowMemory();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        LatLng location = null;

        try {
            addressList = geocoder.getFromLocationName(strAddress, 5);
            if (addressList == null) {
                return null;
            }
            Address address = addressList.get(0);
            location = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return location;
    }
}

