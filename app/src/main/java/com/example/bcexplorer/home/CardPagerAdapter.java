package com.example.bcexplorer.home;

import android.text.Layout;
import android.text.TextDirectionHeuristic;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapterInterface {
    private List<CardView> cardViewList;
    private List<CardItem> cardItemList;
    private float baseElevation;

    public CardPagerAdapter() {
        cardViewList = new ArrayList<>();
        cardItemList = new ArrayList<>();
    }

    public void addCardItem(CardItem cardItem) {
        cardViewList.add(null);
        cardItemList.add(cardItem);
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return cardViewList.get(position);
    }

    @Override
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

        cardView.setOnClickListener(cardItem.getOnClickListener());
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
