package com.example.bcexplorer.home;

public class CardItem {
    private int titleResource, textResource, imageResource;

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
}
