package com.example.bcexplorer.home;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapterInterface {
    private List<CardFragment> cardFragmentList;
    private float baseElevation;

    public CardFragmentPagerAdapter(@NonNull FragmentManager fm, float baseElevation) {
        super(fm);
        cardFragmentList = new ArrayList<>();
        this.baseElevation = baseElevation;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return cardFragmentList.get(position);
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return cardFragmentList.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return cardFragmentList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        cardFragmentList.set(position, (CardFragment) fragment);

        return fragment;
    }
}
