package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityExploreBinding;
import com.example.bcexplorer.global.CustomMapView;
import com.example.bcexplorer.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ExploreActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private ActivityExploreBinding b;
    String LIST_TYPE = Constants.WHITE_ROCK;
    String WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_WHITE_ROCK;
    String PHONE_NUMBER = "";
    String WEBSITE = "";
    String EMAIL = "";
    LatLng locationLatLng;
    String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);


        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("What's New in DT Vancouver");
            b.mainImageview.setImageResource(R.drawable.whats_new_vancouver);
            b.titleTxt.setText("Black + Blue");
            b.middleText.setText("Vancouver is upping the steaks");
            b.middleDesc.setText("Black + Blue is Glowbal Group’s most expansive restaurant to-date.. Sitting next to sister restaurants COAST and Italian Kitchen, they are all located on Vancouver’s emerging restaurant row, Alberni Street.");
            b.gallery1.setImageResource(R.drawable.bb_gallery_1);
            b.gallery2.setImageResource(R.drawable.bb_gallery_2);
            b.addressTxt.setText("1032 Alberni St, Vancouver, BC V6E 1A3");
            PHONE_NUMBER = "+521234567891";
            EMAIL = "infol@blackblue.com";
            WEBSITE = "https://www.glowbalgroup.com/blackblue/";

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_VANCOUVER;
        }

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("What's New in White Rock");
            b.mainImageview.setImageResource(R.drawable.whats_new_white_rock);
            b.titleTxt.setText(" The Pond at BFS");
            b.middleText.setText("Blue Frog Studios");
            b.middleDesc.setText("Canada's Hottest Live Recording Theatre. The world famous multi-media recording studios and event venue located in the quaint beachside arts community of White Rock, BC (just south of Vancouver). This world-class studio has been home to many, many recordings by international and award winning recording artists.");
            b.gallery1.setImageResource(R.drawable.pond_gallery_1);
            b.gallery2.setImageResource(R.drawable.pond_gallery_2);
            b.addressTxt.setText("1328 Johnston Rd, White Rock, BC V4B 3Z2");
            PHONE_NUMBER = "+521234567891";
            EMAIL = "info@bluefrogstudios.ca";
            WEBSITE = "https://www.bluefrogstudios.ca/thepond.html";

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_WHITE_ROCK;

        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("What's New in Whistler");
            b.mainImageview.setImageResource(R.drawable.whats_new_whistler);
            b.titleTxt.setText(" Forged Axe Throwing");
            b.middleText.setText("Like Darts But More Canadian");
            b.middleDesc.setText("Forged Axe Throwing offers drop-ins, party bookings, and league play in Whistler, BC. No experience necessary — just bring closed-toed shoes and an awesome attitude, and we show you the rest.");
            b.gallery1.setImageResource(R.drawable.axe_gallery_1);
            b.gallery2.setImageResource(R.drawable.axe_gallery_2);
            b.addressTxt.setText("1208 Alpha Lake Rd, Whistler, BC V8E 0H7");
            PHONE_NUMBER = "+521234567891";
            EMAIL = "info@dforgedaxe.ca";
            WEBSITE = "https://www.forgedaxe.ca/";

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_WHISTLER;
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationName = b.titleTxt.getText().toString();
        locationLatLng = getLocationFromAddress(this, b.addressTxt.getText().toString());


        b.buttonEmail.setOnClickListener((View view1) -> {
            // Alert stating the feature might not work
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This feature might not work in an emulator");
            builder.setPositiveButton("Continue", (DialogInterface dialogInterface, int i) -> {
                // Send an email; doesn't work in an emulator (tested on Nexus 6 API 30)
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                // Extras
                emailIntent.putExtra(Intent.EXTRA_EMAIL, EMAIL);
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

        b.buttonCall.setOnClickListener((View view1) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String formattedPhoneNumber = PhoneNumberUtils.formatNumber(PHONE_NUMBER);
            builder.setMessage("Call " + formattedPhoneNumber + "?");
            builder.setPositiveButton("Call", (DialogInterface dialogInterface, int i) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
                startActivity(intent);
            });
            builder.setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {
                dialogInterface.cancel();
            });
            AlertDialog alert = builder.create();
            alert.show();
        });

        b.buttonWebsite.setOnClickListener((View view1) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE)));
        });

        initGoogleMap(savedInstanceState);

    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        b.mapView2.onCreate(mapViewBundle);

        Handler handler = new Handler(getMainLooper());
        handler.post(() -> {
            b.mapView2.getMapAsync(this);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        MenuItem item = menu.findItem(R.id.location_save);

        if (Utils.getBoolean(this, WHATS_NEW_LIST_TYPE)) {
            item.setIcon(R.drawable.ic_location_unsave);
        } else {
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
            overridePendingTransition(R.anim.enter_rev, R.anim.exit_rev);
        } else if (id == R.id.location_save) {
            // If save icon is visible (location is not saved)
            if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_save).getConstantState()) {
                item.setIcon(R.drawable.ic_location_unsave); // Change icon to unsave

                // Set saved status in database
                Utils.store(this, WHATS_NEW_LIST_TYPE, true);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            }
            // If unsave icon is visible (location is already saved)
            else if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_unsave).getConstantState()) {
                item.setIcon(R.drawable.ic_location_save); // Change icon to save

                // Set unsaved status in database
                Utils.store(this, WHATS_NEW_LIST_TYPE, false);
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        b.mapView2.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        b.mapView2.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        b.mapView2.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        b.mapView2.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(locationLatLng).title(locationName));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));
    }

    @Override
    public void onPause() {
        b.mapView2.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        b.mapView2.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        b.mapView2.onLowMemory();
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