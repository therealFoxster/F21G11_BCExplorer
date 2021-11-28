package com.example.bcexplorer.homePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bcexplorer.Constants;
import com.example.bcexplorer.ListDetailActivity;
import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // Featured section
    private ViewPager viewPagerFeatured;
    private CardPagerAdapter cardPagerAdapterFeatured;
    private int currentCardFeatured = 0;

    // Popular section
    private ViewPager viewPagerPopular;
    private CardPagerAdapter cardPagerAdapterPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for home fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.whiteRockCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), ListDetailActivity.class)
                        .putExtra(Constants.PARAMS, Constants.WHITE_ROCK));
            }
        });
        view.findViewById(R.id.vancouverCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), ListDetailActivity.class)
                        .putExtra(Constants.PARAMS, Constants.VANCOUVER));
            }
        });
        view.findViewById(R.id.whistlerCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), ListDetailActivity.class)
                        .putExtra(Constants.PARAMS, Constants.WHISTLER));
            }
        });

        // Featured section
        viewPagerFeatured = view.findViewById(R.id.viewPagerHomeFeatured);
        setupFeatured();

        // Popular section
        viewPagerPopular = view.findViewById(R.id.viewPagerHomePopular);
//        setupPopular();

        return view;
    }

    // Dummy onClickListener to test cards
    private View.OnClickListener onClickListenerDummy = (View view) -> {
        Toast.makeText(view.getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();
    };

    private void setupFeatured() {
        // Setting viewPager's height to 222dp
        int heightDP = 222;
        ViewGroup.LayoutParams layoutParams = viewPagerFeatured.getLayoutParams();
        layoutParams.height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDP, getResources().getDisplayMetrics()));
        viewPagerFeatured.setLayoutParams(layoutParams);

        cardPagerAdapterFeatured = new CardPagerAdapter();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // Randomize cards
            int numberOfCards = 5;
            List<Integer> numbers = new ArrayList<>();

            for (int i = 1; i <= numberOfCards; i++)
                numbers.add(i);

            Collections.shuffle(numbers);
            for (int i = 0; i < numberOfCards; i++) {
                Location location = MainActivity.database.locationDAO().getLocationWithID("" + numbers.get(i));
                cardPagerAdapterFeatured.addCardItem(new CardItem(location.getLocationName(), String.format("%s in %s", location.getCategory(), location.getCity()), location.getImage1Name()), onClickListenerDummy);
            }

//            List<Location> locations = MainActivity.database.locationDAO().getAllLocations();
//            for (Location location: locations) {
//                cardPagerAdapterFeatured.addCardItem(new CardItem(location.getLocationName(), String.format("%s in %s", location.getCategory(), location.getCity()), location.getImage1Name()), onClickListenerDummy);
//            }
        });

        viewPagerFeatured.setAdapter(cardPagerAdapterFeatured);

        // Start card autoscroll
        final long DELAY_MS = 500; // Delay before task is executed
        final long PERIOD_MS = 2500; // Time between task executions
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            if (currentCardFeatured == cardPagerAdapterFeatured.getCount()) {
                currentCardFeatured = 0;
            }
            viewPagerFeatured.setCurrentItem(currentCardFeatured++, true);
        };

        Timer timer = new Timer(); // New thread
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, DELAY_MS, PERIOD_MS);

        viewPagerFeatured.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentCardFeatured = position; // Update currentCard number when user interacts with it so that autoscroll scrolls to the correct page
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void setupPopular() {
        // Setting viewPager's height to 325dp
        ViewGroup.LayoutParams layoutParams = viewPagerPopular.getLayoutParams();
        layoutParams.height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 325,getResources().getDisplayMetrics()));
        viewPagerPopular.setLayoutParams(layoutParams);

        cardPagerAdapterPopular = new CardPagerAdapter();

        cardPagerAdapterPopular.addCardItem(new CardItem(getString(R.string.sample_title), getString(R.string.sample_text)), onClickListenerDummy);
        cardPagerAdapterPopular.addCardItem(new CardItem(getString(R.string.sample_title), getString(R.string.sample_text)), onClickListenerDummy);
        cardPagerAdapterPopular.addCardItem(new CardItem(getString(R.string.sample_title), getString(R.string.sample_text)), onClickListenerDummy);

        viewPagerPopular.setAdapter(cardPagerAdapterPopular);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    }

}