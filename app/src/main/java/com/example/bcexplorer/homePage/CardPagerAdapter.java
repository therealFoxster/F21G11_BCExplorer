package com.example.bcexplorer.homePage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {
    private List<CardView> cardViewList;
    private List<CardItem> cardItemList;
    private List<View.OnClickListener> onClickListenerList;

    private float baseElevation;
    public static int MAX_ELEVATION_FACTOR = 1; // Increase to make the CardView smaller

    public CardPagerAdapter() {
        cardViewList = new ArrayList<>();
        cardItemList = new ArrayList<>();
        onClickListenerList = new ArrayList<>();
    }

    public void addCardItem(CardItem cardItem, View.OnClickListener onClickListener) {
        cardViewList.add(null);
        cardItemList.add(cardItem);
        onClickListenerList.add(onClickListener);
    }

    public void addCardItem(CardItem cardItem) {
        addCardItem(cardItem, null);
    }

    public float getBaseElevation() {
        return baseElevation;
    }

    public CardView getCardViewAt(int position) {
        return cardViewList.get(position);
    }

    public int getCount() {
        return cardViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate layout
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.card_view, container, false);
        container.addView(view);

        // Setting card title and content
        CardItem cardItem = cardItemList.get(position);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewContent = view.findViewById(R.id.textViewContent);
        textViewTitle.setText(cardItem.getTitleResource());
        textViewContent.setText(cardItem.getTextResource());
        // TODO: Set card background image (ImageView)

        CardView cardView = view.findViewById(R.id.cardView);

        if (baseElevation == 0)
            baseElevation = cardView.getCardElevation();

        if (onClickListenerList.get(position) != null) // If onClickListener exists for current CardView
            cardView.setOnClickListener(onClickListenerList.get(position));
        cardView.setMaxCardElevation(baseElevation * MAX_ELEVATION_FACTOR);
        cardViewList.set(position, cardView);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        cardViewList.set(position, null);
    }
}
