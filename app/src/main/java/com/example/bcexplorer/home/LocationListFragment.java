package com.example.bcexplorer.home;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationListFragment newInstance(String param1, String param2) {
        LocationListFragment fragment = new LocationListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static String category = "";
    private List<Location> locationList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        locationList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null)
            category = bundle.getString("CATEGORY");

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.locations);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_location_list, container, false);

        ((TextView) view.findViewById(R.id.textViewCategory)).setText(category + "s");

        RecyclerView recyclerViewLocationList = view.findViewById(R.id.recyclerViewLocationList);
        recyclerViewLocationList.setLayoutManager(new LinearLayoutManager(getContext()));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            locationList = MainActivity.database.locationDAO().getLocationsMatchingCategory(category);
            recyclerViewLocationList.setAdapter(new LocationListItemRecyclerViewAdapter(locationList, container, requireContext()));
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAdapter();
    }

    private void setupAdapter() {
        RecyclerView recyclerViewLocationList = getView().findViewById(R.id.recyclerViewLocationList);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            locationList = MainActivity.database.locationDAO().getLocationsMatchingCategory(category);
            getActivity().runOnUiThread(() -> {
                recyclerViewLocationList.setAdapter(new LocationListItemRecyclerViewAdapter(locationList, (ViewGroup) getView().getParent(), requireContext()));
            });
        });
    }
}