package com.example.bcexplorer.utils;

public class ListItemModel {
    String itemTitle;
    String itemDesc;
    int image;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ListItemModel(String itemTitle, String itemDesc, int image) {
        this.itemTitle = itemTitle;
        this.itemDesc = itemDesc;
        this.image = image;

    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public int getImage() {
        return image;
    }
}
