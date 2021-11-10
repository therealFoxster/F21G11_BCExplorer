package com.example.bcexplorer.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class Transformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    private ViewPager viewPager;
    private CardPagerAdapter cardPagerAdapter; // Adapter object that implements CardAdapterInterface
    private float lastOffset;

    public Transformer(ViewPager viewPager, CardPagerAdapter cardPagerAdapter) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        this.cardPagerAdapter = cardPagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentPosition, nextPosition;
        float baseElevation = cardPagerAdapter.getBaseElevation();
        float offset;
        boolean goingLeft = lastOffset > positionOffset; // Going left if the last offset is greater than the offset going to

        if (goingLeft) {
            currentPosition = position + 1; // Current position is the leftmost page displayed + 1 (middle page)
            nextPosition = position; // The position going to is the leftmost page displayed
            offset = 1 - positionOffset;
        } else {
            currentPosition = position;
            nextPosition = position + 1;
            offset = positionOffset;
        }

        // End execution if the current or next position is greater than the number of adapters (overscroll) to prevent crash
        if (currentPosition > cardPagerAdapter.getCount() - 1 || nextPosition > cardPagerAdapter.getCount() - 1)
            return;

        CardView currentCard = cardPagerAdapter.getCardViewAt(currentPosition);
        if (currentCard != null)
            currentCard.setCardElevation(baseElevation + baseElevation * (CardPagerAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - offset));

        CardView nextCard = cardPagerAdapter.getCardViewAt(nextPosition);
        if (nextCard != null)
            nextCard.setCardElevation(baseElevation + baseElevation * (CardPagerAdapter.MAX_ELEVATION_FACTOR - 1) * (offset));

        lastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
