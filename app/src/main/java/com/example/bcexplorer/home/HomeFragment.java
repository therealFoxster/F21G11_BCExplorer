package com.example.bcexplorer.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bcexplorer.R;

import java.util.Timer;
import java.util.TimerTask;

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

        // Featured section
        viewPagerFeatured = view.findViewById(R.id.viewPagerHomeFeatured);
        setupFeatured();

        // Popular section
        viewPagerPopular = view.findViewById(R.id.viewPagerHomePopular);
        setupPopular();

        return view;
    }

    // Dummy onClickListener to test cards
    private View.OnClickListener onClickListenerDummy = (View view) -> {
        Toast.makeText(view.getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();
    };

    private void setupFeatured() {
        cardPagerAdapterFeatured = new CardPagerAdapter();

        // Adding cards to pager adapter
        cardPagerAdapterFeatured.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterFeatured.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterFeatured.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterFeatured.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterFeatured.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);

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
        cardPagerAdapterPopular = new CardPagerAdapter();

        cardPagerAdapterPopular.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterPopular.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);
        cardPagerAdapterPopular.addCardItem(new CardItem(R.string.sample_title, R.string.sample_text), onClickListenerDummy);

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