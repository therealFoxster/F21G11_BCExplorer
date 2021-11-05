package com.example.bcexplorer.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    private ViewPager viewPager;
    private CardAdapterInterface adapter; // Adapter object that implements CardAdapterInterface
    private float lastOffset;

    public ShadowTransformer(ViewPager viewPager, CardAdapterInterface adapter) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        this.adapter = adapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentPosition, nextPosition;
        float baseElevation = adapter.getBaseElevation();
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
        if (currentPosition > adapter.getCount() - 1 || nextPosition > adapter.getCount() - 1)
            return;

        CardView currentCard = adapter.getCardViewAt(currentPosition);
        if (currentCard != null) {
            currentCard.setCardElevation(baseElevation + baseElevation * (CardAdapterInterface.MAX_ELEVATION_FACTOR - 1) * (1 - offset));
        }

        CardView nextCard = adapter.getCardViewAt(nextPosition);
        if (nextCard != null) {
            nextCard.setCardElevation(baseElevation + baseElevation * (CardAdapterInterface.MAX_ELEVATION_FACTOR - 1) * (offset));
        }

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
