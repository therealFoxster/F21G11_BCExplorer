package com.example.bcexplorer.utils;

public class ListItemModel {
    String itemTitle;
    String itemDesc;
    int image;
    public ListItemModel(String itemTitle, String itemDesc, int image) {
        this.itemTitle = itemTitle;
        this.itemDesc = itemDesc;
        this.image = image;
    }

    public String getItemTitle() {
        return itemTitle;
    }
}
