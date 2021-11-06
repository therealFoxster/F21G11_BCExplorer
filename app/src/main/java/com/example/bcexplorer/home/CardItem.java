package com.example.bcexplorer.home;

import android.view.View;

public class CardItem {
    private int titleResource, textResource, imageResource;
    private View.OnClickListener onClickListener;

    public CardItem(int titleResource, int textResource, View.OnClickListener onClickListener) {
        this.titleResource = titleResource;
        this.textResource = textResource;
        this.onClickListener = onClickListener;
    }

    public CardItem(int titleResource, int textResource) {
        this.titleResource = titleResource;
        this.textResource = textResource;
    }

    public CardItem(int titleResource, int textResource, int imageResource) {
        this.titleResource = titleResource;
        this.textResource = textResource;
        this.imageResource = imageResource;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public int getTextResource() {
        return textResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
