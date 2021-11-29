package com.example.bcexplorer.info;

public class InfoItem {
    private int iconImageResource;
    private int textResource;
    private String text;

    public InfoItem(String text) {
        this.text = text;
    }

    public InfoItem(int iconImageResource, int textResource) {
        this.iconImageResource = iconImageResource;
        this.textResource = textResource;
    }

    public int getIconImageResource() {
        return iconImageResource;
    }

    public int getTextResource() {
        return textResource;
    }

    public String getText() {
        return text;
    }
}
