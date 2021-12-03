package com.example.bcexplorer.global;

import static com.example.bcexplorer.global.MainActivity.fragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    CustomMapView mMapView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static String locationID;
    private static String locationName;
    private static String locationAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null)
            locationID = bundle.getString("LOCATION_ID", "error");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);
            locationName = location.getLocationName();
            locationIsSaved = location.isSaved();
            locationAddress = location.getAddress();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        //Setup Maps view
        mMapView = view.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.location);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);

            ImageView imageViewHeader = view.findViewById(R.id.imageViewLocationHeader);
            imageViewHeader.setImageResource(getResources().getIdentifier(location.getImage1Name(), "drawable", container.getContext().getPackageName()));

            TextView textViewName = view.findViewById(R.id.textViewLocationName);
            textViewName.setText(location.getLocationName());
            TextView textViewOverviewHeader = view.findViewById(R.id.textViewLocationOverviewHeader);
            textViewOverviewHeader.setText(location.getOverviewHeader());
            TextView textViewLocationOverviewCont = view.findViewById(R.id.textViewLocationOverviewCont);
            textViewLocationOverviewCont.setText(location.getOverviewContent());

            ViewPager viewPagerLocationImages = view.findViewById(R.id.viewPagerLocationImages);

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

            TextView textViewMaps = view.findViewById(R.id.textViewLocationMaps);
            textViewMaps.setText("Getting to " + location.getLocationName());

            Button buttonCall = view.findViewById(R.id.buttonCall);
            Button buttonEmail = view.findViewById(R.id.buttonEmail);
            Button buttonWebsite = view.findViewById(R.id.buttonWebsite);

            String phone = location.getPhone();
            String email = location.getEmail();

            if (phone == null)
                buttonCall.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            else { // Setup onClickListener
                buttonCall.setOnClickListener((View view1) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    String formattedPhoneNumber = PhoneNumberUtils.formatNumber(phone);
                    builder.setMessage("Call " + formattedPhoneNumber + "?");
                    builder.setPositiveButton("Call", (DialogInterface dialogInterface, int i) -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + location.getPhone()));
                        view.getContext().startActivity(intent);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        Handler handler = new Handler(getContext().getMainLooper());
        handler.post(()-> {
            mMapView.getMapAsync(this);
        });
    }


    private static boolean locationIsSaved = false;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        LocationFragment locationFragment = (LocationFragment) fragmentManager.findFragmentByTag("LOCATION_FRAGMENT");
        LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");

        MenuItem item = menu.findItem(R.id.location_save);

        if (locationFragment == null && savedLocationFragment == null)
            item.setVisible(false);
        else if (item != null)
            item.setVisible(true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);
//            locationIsSaved = location.isSaved();
//            if (location.isSaved())
//                item.setIcon(R.drawable.ic_location_unsave);
//            else
//                item.setIcon(R.drawable.ic_location_save);
        });

        if (locationIsSaved && item != null) {
            item.setIcon(R.drawable.ic_location_unsave);
        }
        else if (!locationIsSaved && item != null) {
            item.setIcon(R.drawable.ic_location_save);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.location_save);

        LocationFragment locationFragment = (LocationFragment) fragmentManager.findFragmentByTag("LOCATION_FRAGMENT");
        LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");
        Fragment locationListLocationFragment = fragmentManager.findFragmentByTag("LOCATION_LIST_LOCATION_FRAGMENT");

        if (MainActivity.bottomNavigationViewPager.getCurrentItem() == 0 && locationFragment == null && locationListLocationFragment == null) // On home page
            item.setVisible(false);
        else if (MainActivity.bottomNavigationViewPager.getCurrentItem() == 1 && savedLocationFragment == null) // On saved page
            item.setVisible(false);
        else if (item != null)
            item.setVisible(true);
//        if(item != null)
//            item.setVisible(true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Location location = MainActivity.database.locationDAO().getLocationWithID(locationID);
            locationIsSaved = location.isSaved();
        });

        if (locationIsSaved && item != null)
            item.setIcon(R.drawable.ic_location_unsave);
        else if (!locationIsSaved && item != null) {
            item.setIcon(R.drawable.ic_location_save);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location_save: // Save button

                // Detaching and reattaching the saved page to refresh its data
//                Fragment savedPage = ((BottomNavigationViewPagerAdapter) bottomNavigationViewPager.getAdapter()).getItem(1);
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.detach(savedPage);
//                fragmentTransaction.attach(savedPage);
//                fragmentTransaction.commit();
//                savedPage.onResume();

                // If save icon is visible (location is not saved)
                if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_save).getConstantState()) {
                    Toast.makeText(getActivity(), String.format("Added %s to saved list", locationName), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), String.format("Removed %s from saved list", locationName), Toast.LENGTH_LONG).show();
                    item.setIcon(R.drawable.ic_location_save); // Change icon to save

                    // Set unsaved status in database
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        MainActivity.database.locationDAO().unsaveLocation(locationID);
                    });
                }

                LocationFragment savedLocationFragment = (LocationFragment) fragmentManager.findFragmentByTag("SAVED_LOCATION_FRAGMENT");

//                if (savedLocationFragment == null)
//                    Log.d("LOCATION_FRAGMENT", "savedLocationFragment is null");
//                else {
//                    Log.d("LOCATION_FRAGMENT", "savedLocationFragment is not null");
//                    if (savedLocationFragment.isVisible())
//                        Log.d("LOCATION_FRAGMENT", "savedLocationFragment is visible");
//                    else Log.d("LOCATION_FRAGMENT", "savedLocationFragment is not visible");
//                }
                if (savedLocationFragment != null) // If in location fragment started by saved page
                    MainActivity.refreshSavedPage();

                break;
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
        map.addMarker(new MarkerOptions().position(getLocationFromAddress(getContext(), locationAddress)).title(locationAddress));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocationFromAddress(getContext(), locationAddress), 15));
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
            // May throw an IOException
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