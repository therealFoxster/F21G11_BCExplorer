package com.example.bcexplorer.home;

public class CardItem {
    private String title, category, imageName;

    public CardItem(String title, String category) {
        this.title = title;
        this.category = category;
    }

    public CardItem(String title, String category, String imageName) {
        this.title = title;
        this.category = category;
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }

}
