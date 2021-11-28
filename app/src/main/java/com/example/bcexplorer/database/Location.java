package com.example.bcexplorer.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class Location {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "locationID")
    private String locationID;
    @ColumnInfo(name = "locationName")
    private String locationName;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "saved")
    private int saved;

    // Images
    @ColumnInfo(name = "image1Name")
    private String image1Name;
    @ColumnInfo(name = "image2Name")
    private String image2Name;
    @ColumnInfo(name = "image3Name")
    private String image3Name;

    // Overview
    @ColumnInfo(name = "overviewHeader")
    private String overviewHeader;
    @ColumnInfo(name = "overviewContent")
    private String overviewContent;

    // Contact
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "website")
    private String website;

    // Default constructor
//    public Location() {
//
//    }

    // Overloaded constructor
    public Location(@NonNull String locationID, String locationName, String category, int saved, String image1Name, String image2Name, String image3Name, String overviewHeader, String overviewContent, String city, String address, String phone, String email, String website) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.category = category;
        this.saved = saved;
        this.image1Name = image1Name;
        this.image2Name = image2Name;
        this.image3Name = image3Name;
        this.overviewHeader = overviewHeader;
        this.overviewContent = overviewContent;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

    @NonNull
    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(@NonNull String locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void save() {
        saved = 1;
    }

    public void unsave() {
        saved = 0;
    }

    public boolean isSaved() {
        return saved == 1;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public String getImage1Name() {
        return image1Name;
    }

    public void setImage1Name(String image1Name) {
        this.image1Name = image1Name;
    }

    public String getImage2Name() {
        return image2Name;
    }

    public void setImage2Name(String image2Name) {
        this.image2Name = image2Name;
    }

    public String getImage3Name() {
        return image3Name;
    }

    public void setImage3Name(String image3Name) {
        this.image3Name = image3Name;
    }

    public String getOverviewHeader() {
        return overviewHeader;
    }

    public void setOverviewHeader(String overviewHeader) {
        this.overviewHeader = overviewHeader;
    }

    public String getOverviewContent() {
        return overviewContent;
    }

    public void setOverviewContent(String overviewContent) {
        this.overviewContent = overviewContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
