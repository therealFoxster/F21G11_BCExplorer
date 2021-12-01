package com.example.bcexplorer.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreditsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreditsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditsFragment newInstance(String param1, String param2) {
        CreditsFragment fragment = new CreditsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.credits);

        locationList = new ArrayList<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            locationList = MainActivity.database.locationDAO().getAllLocations();
        });
    }

    // Hiding save button
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private List<Location> locationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credits, container, false);

        ListView listViewDestinationSources = view.findViewById(R.id.listViewLocationSources);
        CreditsItemAdapter adapter = new CreditsItemAdapter();
        for (Location location: locationList) {
            adapter.addCreditsItem(location.getLocationName(), (View view1) -> {
                // Location name
                Bundle bundle = new Bundle();
                bundle.putString("NAME", location.getLocationName());

                // Text content source
                CreditsItemDetail overviewSource = new CreditsItemDetail("Overview text", location.getOverviewContentSource());
                bundle.putSerializable("OVERVIEW", overviewSource);

                // Image 1 source
                CreditsItemDetail image1Source = new CreditsItemDetail(location.getImage1Name(), location.getImage1Source());
                bundle.putSerializable("IMAGE1", image1Source);

                // Image 2 source
                CreditsItemDetail image2Source;
                if (location.getImage2Name() != null) {
                    if (location.getImage2Source() != null) { // Exists and has source
                        image2Source = new CreditsItemDetail(location.getImage2Name(), location.getImage2Source());
                    } else { // Exists but has no source
                        image2Source = new CreditsItemDetail(location.getImage2Name(), "Unknown");
                    }
                    bundle.putSerializable("IMAGE2", image2Source);
                }

                // Image 3 source
                CreditsItemDetail image3Source;
                if (location.getImage2Name() != null) {
                    if (location.getImage2Source() != null) { // Exists and has source
                        image3Source = new CreditsItemDetail(location.getImage3Name(), location.getImage3Source());
                    } else { // Exists but has no source
                        image3Source = new CreditsItemDetail(location.getImage3Name(), "Unknown");
                    }
                    bundle.putSerializable("IMAGE3", image3Source);
                }

                Intent detailsActivity = new Intent(getContext(), CreditsItemDetailsActivity.class);
                detailsActivity.putExtras(bundle);

                startActivity(detailsActivity);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            });
        }
        // TODO: Add extra credits items (if any) bellow this line

        listViewDestinationSources.setAdapter(adapter);
        return view;
    }
}