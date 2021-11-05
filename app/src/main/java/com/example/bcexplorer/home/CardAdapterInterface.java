package com.example.bcexplorer.home;

import androidx.cardview.widget.CardView;

public interface CardAdapterInterface {
    int MAX_ELEVATION_FACTOR = 1; // Increase to make the CardView smaller
    float getBaseElevation();
    CardView getCardViewAt(int position);
    int getCount();
}
