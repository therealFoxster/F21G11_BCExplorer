package com.example.bcexplorer.global;

import static com.example.bcexplorer.MainActivity.fragmentManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    CustomMapView mMapView;

    private static String locationID;
    private static String locationName;
    private static boolean locationIsSaved = false;
    private static String locationAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.location);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            locationID = bundle.getString("LOCATION_ID", "error");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);
            locationName = location.getLocationName();
            locationIsSaved = location.isSaved();
            locationAddress = location.getAddress();
        });

        mMapView = findViewById(R.id.mapViewLocation);
        initGoogleMap(savedInstanceState);

        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);

            ImageView imageViewHeader = findViewById(R.id.imageViewLocationHeader);
            imageViewHeader.setImageResource(getResources().getIdentifier(location.getImage1Name(), "drawable", LocationActivity.this.getPackageName()));

            TextView textViewName = findViewById(R.id.textViewLocationName);
            textViewName.setText(location.getLocationName());
            TextView textViewOverviewHeader = findViewById(R.id.textViewLocationOverviewHeader);
            textViewOverviewHeader.setText(location.getOverviewHeader());
            TextView textViewLocationOverviewCont = findViewById(R.id.textViewLocationOverviewCont);
            textViewLocationOverviewCont.setText(location.getOverviewContent());

            ViewPager viewPagerLocationImages = findViewById(R.id.viewPagerLocationImages);

            ViewPagerLocationImagesAdapter adapter = new ViewPagerLocationImagesAdapter();
            if (location.getImage2Name() != null)
                adapter.addImageName(location.getImage2Name());
            if (location.getImage3Name() != null)
                adapter.addImageName(location.getImage3Name());
            adapter.addImageName(location.getImage1Name());
            viewPagerLocationImages.setAdapter(adapter);

            viewPagerLocationImages.setClipToPadding(false);
            viewPagerLocationImages.setPadding(80,0, 80, 0);
            viewPagerLocationImages.setPageMargin(-10);

            TextView textViewMaps = findViewById(R.id.textViewLocationMaps);
            textViewMaps.setText("Getting to " + location.getLocationName());

            Button buttonCall = findViewById(R.id.buttonCall);
            Button buttonEmail = findViewById(R.id.buttonEmail);
            Button buttonWebsite = findViewById(R.id.buttonWebsite);

            String phone = location.getPhone();
            String email = location.getEmail();

            if (phone == null)
                buttonCall.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            else { // Setup onClickListener
                buttonCall.setOnClickListener((View view1) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                    String formattedPhoneNumber = PhoneNumberUtils.formatNumber(phone);
                    builder.setMessage("Call " + formattedPhoneNumber + "?");
                    builder.setPositiveButton("Call", (DialogInterface dialogInterface, int i) -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + location.getPhone()));
                        startActivity(intent);
                    });
                    builder.setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {
                        dialogInterface.cancel();
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                });
            }
            if (email == null)
                buttonEmail.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            else { // Setup onClickListener
                buttonEmail.setOnClickListener((View view1) -> {
                    // Alert stating the feature might not work
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                    builder.setMessage("This feature might not work in an emulator");
                    builder.setPositiveButton("Continue", (DialogInterface dialogInterface, int i) -> {
                        // Send an email; doesn't work in an emulator (tested on Nexus 6 API 30)
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");

                        // Extras
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, location.getEmail());
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Content");

                        startActivity(emailIntent);
                    });
                    builder.setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {
                        dialogInterface.cancel();
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                });
            }

            buttonWebsite.setOnClickListener((View view1) -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(location.getWebsite())));
            });

        });
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        Handler handler = new Handler(this.getMainLooper());
        handler.post(()-> {
            mMapView.getMapAsync(this);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_location_view, menu);

        MenuItem item = menu.findItem(R.id.location_save);

        if (locationIsSaved && item != null) {
            item.setIcon(R.drawable.ic_location_unsave);
        }
        else if (!locationIsSaved && item != null) {
            item.setIcon(R.drawable.ic_location_save);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_save: // Save button

                // If save icon is visible (location is not saved)
                if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_save).getConstantState()) {
                    Toast.makeText(this, String.format("Added %s to saved list", locationName), Toast.LENGTH_LONG).show();
                    item.setIcon(R.drawable.ic_location_unsave); // Change icon to unsave

                    // Set saved status in database
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        MainActivity.database.locationDAO().saveLocation(locationID);
                        MainActivity.database.locationDAO().setSavedTime(locationID, (int) (System.currentTimeMillis()/1000)); // Saved time
                    });
                }
                // If unsave icon is visible (location is already saved)
                else if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_unsave).getConstantState()) {
                    Toast.makeText(this, String.format("Removed %s from saved list", locationName), Toast.LENGTH_LONG).show();
                    item.setIcon(R.drawable.ic_location_save); // Change icon to save

                    // Set unsaved status in database
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        MainActivity.database.locationDAO().unsaveLocation(locationID);
                    });
                }
                break;
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.none, R.anim.exit_rev);
                return true;
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

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(getLocationFromAddress(this, locationAddress)).title(locationName));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocationFromAddress(this, locationAddress), 15));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
            location = new LatLng(address.getLatitude(), address.getLongitude() );

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return location;
    }
}